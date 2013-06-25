/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package recommendationStrategy;

/**
 * @author Wayne Rijsdijk
 */
public class RecommendationDistanceStrategy {
	private IStrategy strategy;
	
	public RecommendationDistanceStrategy(IStrategy strategy) {
		this.strategy = strategy;
	}
	
	public IStrategy getStrategy() {
		return this.strategy;
	}
}