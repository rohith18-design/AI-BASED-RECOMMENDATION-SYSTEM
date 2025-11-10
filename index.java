//FILE STRUCTURE 

RecommendationSystem/
│
├── src/
│   └── RecommendDemo.java
├── data/
│   └── product_ratings.csv
└── README.md

//SAMPLE DATA — 
product_ratings.csv

1,101,4.5
1,102,2.0
1,103,3.5
2,101,2.5
2,102,4.0
2,104,4.5
3,101,4.0
3,103,2.0
3,104,5.0
4,102,3.5
4,103,4.0
4,104,4.5

//format →
userID, productID, rating

//JAVA CODE

// RecommendDemo.java
// A simple recommendation system using Apache Mahout for collaborative filtering
// Author: [Your Name]
// Date: [Insert Date]

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecommendDemo {
    public static void main(String[] args) throws IOException, TasteException {

        //  Load dataset (CSV file: userID, itemID, rating)
        File dataFile = new File("data/product_ratings.csv");
        DataModel model = new FileDataModel(dataFile);

        //  Compute similarity between users using Pearson correlation
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

        //  Define neighborhood (top 2 most similar users)
        NearestNUserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

        //  Build the recommender
        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        // 5️⃣ Generate recommendations for a specific user (e.g., user ID = 1)
        int userID = 1;
        int numRecommendations = 3;

        List<RecommendedItem> recommendations = recommender.recommend(userID, numRecommendations);

        System.out.println("Recommended items for User " + userID + ":");
        for (RecommendedItem recommendation : recommendations) {
            System.out.printf("Product ID: %d, Predicted Preference: %.2f%n",
                    recommendation.getItemID(),
                    recommendation.getValue());
        }
    }
}
