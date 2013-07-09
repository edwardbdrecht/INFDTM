package datamining;

import itemitem.ItemItem;
import itemitem.RecommendationResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import useritem.CreateSimilarityMap;
import useritem.UserItemRecommendations;

public class DataMining {

    static TreeMap<Integer, UserPreferences> userPreferences;
    static TreeMap<Integer, Movie> movies;
    static TopProducts tp;

    public static void main(String[] args) {
        System.out.println("Please select a movies file");
        File moviesFile = new ChooseFile().getFile();

        System.out.println("Please select a datafile");
        File dataFile = new ChooseFile().getFile();
        //File dataFile = new File("C:\\Users\\Wayne Rijsdijk\\Desktop\\datamining.txt");

        userPreferences = new TreeMap<Integer, UserPreferences>();
        tp = new TopProducts();

        movies = DataMining.getMovies(moviesFile);

        int lineNumber = 0;
        StringTokenizer st;
        Scanner scanner = null;
        //Use a scanner because a BufferedReader is not necessary for this type of datasets (?)
        try {
            scanner = new Scanner(dataFile);
            while (scanner.hasNextLine()) {
                lineNumber++;
                String nextLine = scanner.nextLine();
                if (nextLine == null) { //makes sure that no null value is passed to the StringTokenizer. This is a part of the data validation system.
                    continue;
                }
                st = new StringTokenizer(nextLine, ","); //use StringTokenizer, because that's twice as fast as String.split() when using simple patterns

                int userId = Integer.parseInt(st.nextToken());
                int itemId = Integer.parseInt(st.nextToken());
                float rating = Float.parseFloat(st.nextToken());

                UserPreferences up = userPreferences.get(userId); //get userPreferences from userId out of the TreeMap
                if (up == null) { //check if the userPreferences was not existent in the TreeMap
                    up = new UserPreferences(userId); //make new userPreferences
                }
                up.addElement(itemId, rating); //add element to userPreferences
                userPreferences.put(userId, up); //put it back in the TreeMap

                //System.out.println(itemId + " added");
                //tp.addProduct(itemId); //add item to topProducts class for creating top product lists
            }

            //DEBUG
            for (UserPreferences up : userPreferences.values()) {
                System.out.println(up.toString());
            }

            //int[] popularProducts = tp.getPopularProducts();
            //for(int popularProduct : popularProducts) {
            //	System.out.println(Integer.toString(popularProduct));
            //}
        } catch (FileNotFoundException e) {
            System.out.println("The selected file could not be found!");
        } catch (NumberFormatException e) {
            System.out.println("It look likes the data is corrupted! (Line: " + lineNumber + ")");
            e.getMessage();
            e.getStackTrace();
        } catch (Exception e) {
            System.out.println("A unknown error occured: " + e.getMessage());
            e.getStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        /*Set<Integer> ks = movies.keySet();
         Iterator it = ks.iterator();
         while(it.hasNext())
         {
         Movie m = movies.get(it.next());
         System.out.println("ID: "+movies.get(it.next()).id+"NAME:"+movies.get(it.next()).name+"");
         //System.out.println("ID: "+m.id+" NAME: "+m.name);
         }*/

        /*for(Movie up : movies.values()) {
         System.out.println(up.toString());
         }*/

        System.out.println("Starting UserItem");
        CreateSimilarityMap c = new CreateSimilarityMap(userPreferences);
        //c.printSimilarityMap(c.getSimilarityMap());

        System.out.println("Starting Recommendation");
        long beforeUserItem = System.currentTimeMillis();
        TopProducts recommendedItems = UserItemRecommendations.getRecommendations(userPreferences, c.getSimilarityMap().get(7));
        long afterUserItem = System.currentTimeMillis();
        long diffUserItem = afterUserItem - beforeUserItem;
        System.out.println("Used time: " + diffUserItem + "ms (" + afterUserItem + " - " + beforeUserItem + ")");
        System.out.println("Recommended userItem items:");

        String[] recommendedItemz = recommendedItems.getPopularProducts(3);
        Collections.reverse(Arrays.asList(recommendedItemz));
        for (String recommendedItem : recommendedItemz) {
            System.out.println("Product: " + movies.get(Integer.parseInt(recommendedItem)).name);
        }

        //RecordDataNoSimilarlyItems rd = userPreferences.get(17).noSimilarItems(userPreferences.get(15).getItemIds(), userPreferences.get(15).getRatings());
        //System.out.println("DEBUG");
        //for(int recommendedItem : rd.items) {
        //	System.out.println("Product ID: " + recommendedItem);
        //}

        //RecommendationDistanceStrategy rds = new RecommendationDistanceStrategy(new Euclideon());
        //float kaas = rds.getStrategy().execute(userPreferences.get(13), userPreferences.get(16));
        //System.out.println(kaas);

        /*
         * Build ItemItem with given users
         */
        System.out.println("Starting ItemItem");
        ItemItem item = new ItemItem();
        item.buildRatingTable(userPreferences);
        System.out.println("Starting OneSlope creation");
        item.createAndFillOneSlope();
        //item.printALl();

        /*
         * Get ItemItem based recommendation for specified user
         */
        int userIdToTest = 384;
        System.out.println("Starting recommendation");
        long before = System.currentTimeMillis();
        RecommendationResult[] r = item.getRecommendation(userIdToTest);
        long after = System.currentTimeMillis();
        long diff = after - before;
        System.out.println("Used time: " + diff + "ms (" + after + " - " + before + ")");

        /*
         * Print recommendation
         */
        System.out.println("------- ITEMITEM RECOMMENDATION FOR USER " + userIdToTest + " -------");
        for (int i = 0; i < r.length; i++) {
            System.out.print((i + 1) + ". Item: " + movies.get(r[i].getItemId()).name + " Value: " + r[i].getRecomValue());
            System.out.println(" ");
        }

        System.exit(0);
    }

    public static TreeMap<Integer, Movie> getMovies(File moviesFile) {
        TreeMap<Integer, Movie> movies = new TreeMap<>();

        int lineNumber = 0;
        StringTokenizer st;
        BufferedReader scanner = null;
        String line;
        //Use a scanner because a BufferedReader is not necessary for this type of datasets (?)
        try {
            scanner = new BufferedReader(new FileReader(moviesFile));
            while ((line = scanner.readLine()) != null) {
                lineNumber++;
                //String nextLine = scanner.nextLine();
                //if(nextLine == null) { //makes sure that no null value is passed to the StringTokenizer. This is a part of the data validation system.
                // continue;
                //}
                st = new StringTokenizer(line, "|"); //use StringTokenizer, because that's twice as fast as String.split() when using simple patterns

                Movie movie = new Movie();
                movie.id = Integer.parseInt(st.nextToken());
                movie.name = st.nextToken();
                movie.date = st.nextToken();
                movie.url = st.nextToken();

                movies.put(movie.id, movie); //put it in the TreeMap
            }
        } catch (FileNotFoundException e) {
            System.out.println("The selected file could not be found!");
        } catch (NumberFormatException e) {
            System.out.println("It look likes the data is corrupted! (Line: " + lineNumber + ")");
            e.getMessage();
            e.getStackTrace();
        } catch (Exception e) {
            System.out.println("A unknown error occured: " + e.getMessage());
            e.getStackTrace();
        } finally {
            if (scanner != null) {
                try {
                    scanner.close();
                } catch (IOException ex) {
                    Logger.getLogger(DataMining.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return movies;
    }
}