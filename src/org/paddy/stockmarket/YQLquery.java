package org.paddy.stockmarket;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.paddy.stockmarket.util.json.Query;
import org.paddy.stockmarket.util.json.QueryContainer;

/**
 *
 * @author paddy
 */
public class YQLquery 
{
    /**
     * 
     * @param request String
     * @return Query
     */
    protected static Query yqlQueryResult(String request)
    {
        Query query = null;
        try
        {
            URL url = new URL(request);
            try
            {
                InputStreamReader isr;
                BufferedReader br;
                isr = new InputStreamReader(url.openStream());
                br = new BufferedReader(isr);
                String inputLine;
                String returnString;
                returnString = "";
                while ((inputLine = br.readLine()) != null)
                {
                        returnString += inputLine;
                }
                br.close();
                QueryContainer queryContainer = new Gson().fromJson(returnString, QueryContainer.class);
                query = queryContainer.getQuery();
                //System.out.println(query.getCount());
            }
            catch(IOException ioe)
            {
                    System.err.println(ioe);
            }
        }
        catch(MalformedURLException mue)
        {
                System.err.println(mue);
        }
        return query;
    }
}
