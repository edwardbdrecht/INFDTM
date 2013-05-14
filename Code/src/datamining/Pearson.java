package datamining;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Wayne Rijsdijk
 */
public class Pearson {
	public static Map<Integer, Pearson> getPearsonMap(UserPreferences thisUser, UserPreferences[] allUsers) {
		Map<Integer, Pearson> pearsons = new TreeMap<Integer, Pearson>();
		for (int n = 0; n < allUsers.length; n++) {
			Pearson p = new Pearson(UP[i], UP[j]);
			if (p.pearson >= minPearson || p.pearson <= (-minPearson)) {
				Map<Integer, Pearson> map;
				if (pearsons.containsKey(p.userId1)) {
					map = pearsons.get(p.userId1);
				} else {
					map = new TreeMap<Integer, Pearson>();
					pearsons.put(p.userId1, map);
				}
				map.put(p.userId2, p);
			}
		}
	}
}
