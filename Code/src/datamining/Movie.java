package datamining;

/**
 *
 * @author Wayne Rijsdijk
 */
class Movie {
	public int id;
	public String name;
	public String date;
	public String url;
        
	@Override
	public String toString() {
                return "MovieID: " + id + " | Name: " + name;
	}
}
