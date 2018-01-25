package DAL;

import static outpatient.Helper.getConfigTxt;

/**
 * Created by Chaos on 6.6.2017..
 */
public class RepositoryFactory {
    private static String configPath = "repoConfig.txt";

    public static IRepo GetInstance() {
        String configData = getConfigTxt(configPath);

        switch(configData){
            case "1":
                return new RepositoryDatabase();
            default:
                return new RepositoryDatabase();
        }

    }

}