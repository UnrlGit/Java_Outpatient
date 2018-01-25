package outpatient;

import java.io.*;

/**
 * Created by Chaos on 13.6.2017..
 */
public class Helper {
    public static String getConfigTxt(String configPath) {
        String str = "1";
        try {
            FileReader fReader = new FileReader(configPath);
            BufferedReader br = new BufferedReader(fReader);

            str = br.readLine();
            if(str == null || (str != "1" && str !="2")){
                str = "2";
                Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(configPath), "utf-8"));
                writer.write(str);
                writer.close();
            }

        } catch (FileNotFoundException e) {
            //if file does not exist - create new file and set DB
            try {
                Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(configPath), "utf-8"));

                writer.write(str);
                writer.close();

            } catch (IOException e1) {

                e1.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
