package com.example.mealbuddy.apitools;

import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import com.example.mealbuddy.apitools.model.RouteModels;
import com.example.mealbuddy.apitools.model.RouteModels.RegistrationReply;
import com.example.mealbuddy.apitools.model.RouteModels.Registration;
import  com.example.mealbuddy.apitools.model.RouteModels.Login;
import  com.example.mealbuddy.apitools.model.RouteModels.LoginReply;
import  com.example.mealbuddy.apitools.model.RouteModels.Forgot;
import  com.example.mealbuddy.apitools.model.RouteModels.ForgotReply;
import  com.example.mealbuddy.apitools.model.RouteModels.ResetPass;
import  com.example.mealbuddy.apitools.model.RouteModels.ResetReply;
import  com.example.mealbuddy.apitools.model.RouteModels.ConfirmRegisterReply;
import  com.example.mealbuddy.apitools.model.RouteModels.ConfirmRegister;
import  com.example.mealbuddy.apitools.model.RouteModels.EditGroceryList;
import  com.example.mealbuddy.apitools.model.RouteModels.GroceryList;

public class MealBuddyApiTools  {

    private URL _baseUrl = null;

    public MealBuddyApiTools(){
        try {
            this._baseUrl = new URL("https://prj666.mystudentlab.ca:6576/");
        } catch (Exception e) {
            String category = "URL Error";
            String messaage = e.toString();
            Log.d(category, messaage);
        }
    }

    public RegistrationReply registerUser(Registration _model){

        RegistrationReply reply = new RegistrationReply();
        try {
            //created connection
            String registerEndpoint = "profile/register";
            URL url = new URL(this._baseUrl.toString() + registerEndpoint);
            HttpsURLConnection myCon = (HttpsURLConnection) url.openConnection();
            // set headers
            myCon.setRequestMethod("POST");
            myCon.setRequestProperty("Content-Type", "application/json");
            // write JSON string to body
            OutputStream body = myCon.getOutputStream();
            OutputStreamWriter bodyWriter = new OutputStreamWriter(body, "UTF-8");
            bodyWriter.write(_model.getJson());
            bodyWriter.flush();
            bodyWriter.close();
            body.close();
            // Send webapi request
            myCon.connect();
            //get reply
            try {
                InputStreamReader inputReader = new InputStreamReader(myCon.getInputStream(),"utf-8");
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = buffReader.readLine();
                //create JSON object from reply
                JSONObject jsonObj = new JSONObject(line);
                reply.setStatus(jsonObj.optString("status"));
                reply.setMessage(jsonObj.optString("message"));
                reply.setApiKey((jsonObj.optString("apiKey")));
                reply.setUsername(jsonObj.optString("username"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return reply;
    }

    public RouteModels.PostRecipeReply postRecipe(RouteModels.PostRecipe _model){

        RouteModels.PostRecipeReply reply = new RouteModels.PostRecipeReply();
        try {
            //created connection
            String registerEndpoint = "recipe/new";
            URL url = new URL(this._baseUrl.toString() + registerEndpoint);
            HttpsURLConnection myCon = (HttpsURLConnection) url.openConnection();
            // set headers
            myCon.setRequestMethod("POST");
            myCon.setRequestProperty("Content-Type", "application/json");
            // write JSON string to body
            OutputStream body = myCon.getOutputStream();
            OutputStreamWriter bodyWriter = new OutputStreamWriter(body, "UTF-8");
            bodyWriter.write(_model.getJson());
            bodyWriter.flush();
            bodyWriter.close();
            body.close();
            // Send webapi request
            myCon.connect();
            //get reply
            try {
                InputStreamReader inputReader = new InputStreamReader(myCon.getInputStream(),"utf-8");
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = buffReader.readLine();
                //create JSON object from reply
                JSONObject jsonObj = new JSONObject(line);
                reply.setStatus(jsonObj.optString("status"));
                reply.setMessage(jsonObj.optString("message"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return reply;
    }

    public RouteModels.GetRecipeByIdReply getRecipeById(RouteModels.GetRecipeById _model){

        RouteModels.GetRecipeByIdReply reply = new RouteModels.GetRecipeByIdReply();
        try {
            //created connection
            String registerEndpoint = "recipe/byname";
            URL url = new URL(this._baseUrl.toString() + registerEndpoint);
            HttpsURLConnection myCon = (HttpsURLConnection) url.openConnection();
            // set headers
            myCon.setRequestMethod("POST");
            myCon.setRequestProperty("Content-Type", "application/json");
            // write JSON string to body
            OutputStream body = myCon.getOutputStream();
            OutputStreamWriter bodyWriter = new OutputStreamWriter(body, "UTF-8");
            bodyWriter.write(_model.getJson());
            bodyWriter.flush();
            bodyWriter.close();
            body.close();
            // Send webapi request
            myCon.connect();
            //get reply
            try {
                InputStreamReader inputReader = new InputStreamReader(myCon.getInputStream(),"utf-8");
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = buffReader.readLine();
                //create JSON object from reply
                JSONObject jsonObj = new JSONObject(line);
                reply.setStatus(jsonObj.optString("status"));
                reply.setMessage(jsonObj.optString("message"));
                reply.setUser(jsonObj.optString("userid"));
                reply.setRecipes(jsonObj.optJSONArray("recipes"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return reply;
    }



    public RouteModels.GetRecipeByIdReply2 getRecipeById2(RouteModels.GetRecipeById2 _model){

        RouteModels.GetRecipeByIdReply2 reply = new RouteModels.GetRecipeByIdReply2();
        try {
            //created connection
            String registerEndpoint = "recipe/byid";
            URL url = new URL(this._baseUrl.toString() + registerEndpoint);
            HttpsURLConnection myCon = (HttpsURLConnection) url.openConnection();
            // set headers
            myCon.setRequestMethod("POST");
            myCon.setRequestProperty("Content-Type", "application/json");
            // write JSON string to body
            OutputStream body = myCon.getOutputStream();
            OutputStreamWriter bodyWriter = new OutputStreamWriter(body, "UTF-8");
            bodyWriter.write(_model.getJson());
            bodyWriter.flush();
            bodyWriter.close();
            body.close();
            // Send webapi request
            myCon.connect();
            //get reply
            try {
                InputStreamReader inputReader = new InputStreamReader(myCon.getInputStream(),"utf-8");
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = buffReader.readLine();
                //create JSON object from reply
                JSONObject jsonObj = new JSONObject(line);
                reply.setStatus(jsonObj.optString("status"));
                reply.setMessage(jsonObj.optString("message"));
                reply.setRecipeId(jsonObj.optString("id"));

                reply.setCuisine(jsonObj.optString("cuisine"));
                reply.setDiet(jsonObj.optString("diet"));
                reply.setType(jsonObj.optString("type"));
                reply.setDescription(jsonObj.optString("description"));
                reply.setDescription(jsonObj.optString("name"));
                reply.setMealItem(jsonObj.optString("meal_time"));
                reply.setIntolerances("intolerances");
                reply.setNutritionInfo("nutrition_info");
                reply.setIngredients(jsonObj.optJSONArray("ingredients"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return reply;
    }




    //login reply function
    public LoginReply userLogin(Login _model){
        LoginReply reply = new LoginReply();
        try{
            String loginEndpoint = "login";
            URL url = new URL(this._baseUrl.toString() + loginEndpoint);
            HttpsURLConnection myCon = (HttpsURLConnection) url.openConnection();
            myCon.setRequestMethod("POST");
            myCon.setRequestProperty("Content-Type", "application/json");
            OutputStream body = myCon.getOutputStream();
            OutputStreamWriter bodyWriter = new OutputStreamWriter(body, "UTF-8");
            bodyWriter.write(_model.getJson()); //write login model to post body
            bodyWriter.flush();
            bodyWriter.close();
            body.close();
            //send api request
            myCon.connect();
            // get api response
            try{
                InputStreamReader inputReader = new InputStreamReader(myCon.getInputStream(),"utf-8");
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = buffReader.readLine();
                //convert output string to JSON
                JSONObject jsonObj = new JSONObject(line);
                //test in console
                System.out.println("Status: "  + jsonObj.optString("status"));
                System.out.println("message: "  + jsonObj.optString("message"));
                System.out.println("api_key: "  + jsonObj.optString("api_key"));

                //extract data from response json and set it in our reply model
                reply.setStatus(jsonObj.optString("status"));
                reply.setMessage(jsonObj.optString("message"));
                reply.setGroceryListStatus(jsonObj.optString("grocery_list_status"));
                reply.setApiKey((jsonObj.optString("api_key")));
                reply.setUsername(jsonObj.optString("username"));
                reply.setDefaultGroceryList(jsonObj.optString("default_grocery_name"));
                reply.setDgljson(jsonObj.optJSONArray("default_grocery_list"));
                reply.setGroceryListsjson(jsonObj.optJSONArray("grocery_lists"));

            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return reply; //return login reply message model
    }
    //forgot username reply
    public ForgotReply forgotUserReply(Forgot _model){
        ForgotReply reply = new ForgotReply();
        try{
            String loginEndpoint = "login/forgot/user";
            URL url = new URL(this._baseUrl.toString() + loginEndpoint);
            HttpsURLConnection myCon = (HttpsURLConnection) url.openConnection();
            myCon.setRequestMethod("POST");
            myCon.setRequestProperty("Content-Type", "application/json");
            OutputStream body = myCon.getOutputStream();
            OutputStreamWriter bodyWriter = new OutputStreamWriter(body, "UTF-8");
            bodyWriter.write(_model.getJson()); //write forgot model to post body
            bodyWriter.flush();
            bodyWriter.close();
            body.close();
            //send api request
            myCon.connect();
            // get api response
            try{
                InputStreamReader inputReader = new InputStreamReader(myCon.getInputStream(),"utf-8");
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = buffReader.readLine();
                //convert output string to JSON
                JSONObject jsonObj = new JSONObject(line);
                //test in console
                System.out.println("Status: "  + jsonObj.optString("status"));
                System.out.println("message: "  + jsonObj.optString("message"));
                //extract data from response json and set it in our reply model
                reply.setStatus(jsonObj.optString("status"));
                reply.setMessage(jsonObj.optString("message"));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return reply; //return login reply message model
    }
    //forgot password reply
    public ForgotReply forgotPassReply(Forgot _model){
        ForgotReply reply = new ForgotReply();
        try{
            String loginEndpoint = "login/forgot/pass";
            URL url = new URL(this._baseUrl.toString() + loginEndpoint);
            HttpsURLConnection myCon = (HttpsURLConnection) url.openConnection();
            myCon.setRequestMethod("POST");
            myCon.setRequestProperty("Content-Type", "application/json");
            OutputStream body = myCon.getOutputStream();
            OutputStreamWriter bodyWriter = new OutputStreamWriter(body, "UTF-8");
            bodyWriter.write(_model.getJson()); //write forgot model to post body
            bodyWriter.flush();
            bodyWriter.close();
            body.close();
            //send api request
            myCon.connect();
            // get api response
            try{
                InputStreamReader inputReader = new InputStreamReader(myCon.getInputStream(),"utf-8");
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = buffReader.readLine();
                //convert output string to JSON
                JSONObject jsonObj = new JSONObject(line);
                //test in console
                System.out.println("Status: "  + jsonObj.optString("status"));
                System.out.println("message: "  + jsonObj.optString("message"));
                //extract data from response json and set it in our reply model
                reply.setStatus(jsonObj.optString("status"));
                reply.setMessage(jsonObj.optString("message"));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return reply; //return login reply message model
    }

    //check token reply
    public ForgotReply checkToken(Forgot _model){
        ForgotReply reply = new ForgotReply();
        try{
            String loginEndpoint = "login/forgot/checktoken";
            URL url = new URL(this._baseUrl.toString() + loginEndpoint);
            HttpsURLConnection myCon = (HttpsURLConnection) url.openConnection();
            myCon.setRequestMethod("POST");
            myCon.setRequestProperty("Content-Type", "application/json");
            OutputStream body = myCon.getOutputStream();
            OutputStreamWriter bodyWriter = new OutputStreamWriter(body, "UTF-8");
            bodyWriter.write(_model.getJson()); //write forgot model to post body
            bodyWriter.flush();
            bodyWriter.close();
            body.close();
            //send api request
            myCon.connect();
            // get api response
            try{
                InputStreamReader inputReader = new InputStreamReader(myCon.getInputStream(),"utf-8");
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = buffReader.readLine();
                //convert output string to JSON
                JSONObject jsonObj = new JSONObject(line);
                //test in console
                System.out.println("Status: "  + jsonObj.optString("status"));
                System.out.println("message: "  + jsonObj.optString("message"));
                //extract data from response json and set it in our reply model
                reply.setStatus(jsonObj.optString("status"));
                reply.setMessage(jsonObj.optString("message"));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return reply; //return login reply message model
    }

    //reset password reply
    public ResetReply resetPassword(ResetPass _model){
        ResetReply reply = new ResetReply();
        try{
            String loginEndpoint = "login/forgot/resetpass";
            URL url = new URL(this._baseUrl.toString() + loginEndpoint);
            HttpsURLConnection myCon = (HttpsURLConnection) url.openConnection();
            myCon.setRequestMethod("POST");
            myCon.setRequestProperty("Content-Type", "application/json");
            OutputStream body = myCon.getOutputStream();
            OutputStreamWriter bodyWriter = new OutputStreamWriter(body, "UTF-8");
            bodyWriter.write(_model.getJson()); //write forgot model to post body
            bodyWriter.flush();
            bodyWriter.close();
            body.close();
            //send api request
            myCon.connect();
            // get api response
            try{
                InputStreamReader inputReader = new InputStreamReader(myCon.getInputStream(),"utf-8");
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = buffReader.readLine();
                //convert output string to JSON
                JSONObject jsonObj = new JSONObject(line);
                //test in console
                System.out.println("Status: "  + jsonObj.optString("status"));
                System.out.println("message: "  + jsonObj.optString("message"));
                System.out.println("apiKey: "  + jsonObj.optString("apiKey"));
                //extract data from response json and set it in our reply model
                reply.setStatus(jsonObj.optString("status"));
                reply.setMessage(jsonObj.optString("message"));
                reply.setApiKey(jsonObj.optString("apiKey"));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return reply; //return login reply message model
    }

    //check token reply
    public ConfirmRegisterReply checkRToken(ConfirmRegister _model){
        ConfirmRegisterReply reply = new ConfirmRegisterReply();
        try{
            String loginEndpoint = "profile/register/confirm";
            URL url = new URL(this._baseUrl.toString() + loginEndpoint);
            HttpsURLConnection myCon = (HttpsURLConnection) url.openConnection();
            myCon.setRequestMethod("POST");
            myCon.setRequestProperty("Content-Type", "application/json");
            OutputStream body = myCon.getOutputStream();
            OutputStreamWriter bodyWriter = new OutputStreamWriter(body, "UTF-8");
            bodyWriter.write(_model.getJson()); //write  model to post body
            bodyWriter.flush();
            bodyWriter.close();
            body.close();
            //send api request
            myCon.connect();
            // get api response
            try{
                InputStreamReader inputReader = new InputStreamReader(myCon.getInputStream(),"utf-8");
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = buffReader.readLine();
                //convert output string to JSON
                JSONObject jsonObj = new JSONObject(line);
                //test in console
                System.out.println("Status: "  + jsonObj.optString("status"));
                System.out.println("message: "  + jsonObj.optString("message"));
                //extract data from response json and set it in our reply model
                reply.setStatus(jsonObj.optString("status"));
                reply.setMessage(jsonObj.optString("message"));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return reply; //return login reply message model
    }

    //add grocery list item..
    public RouteModels.AddGroceryListReply addGroceryListItem(EditGroceryList _model){
        RouteModels.AddGroceryListReply reply = new RouteModels.AddGroceryListReply();
        try{
            String loginEndpoint = "grocerylist/add";
            URL url = new URL(this._baseUrl.toString() + loginEndpoint);
            HttpsURLConnection myCon = (HttpsURLConnection) url.openConnection();
            myCon.setRequestMethod("POST");
            myCon.setRequestProperty("Content-Type", "application/json");
            OutputStream body = myCon.getOutputStream();
            OutputStreamWriter bodyWriter = new OutputStreamWriter(body, "UTF-8");
            bodyWriter.write(_model.getJson()); //write  model to post body
            bodyWriter.flush();
            bodyWriter.close();
            body.close();
            //send api request
            myCon.connect();
            // get api response
            try{
                InputStreamReader inputReader = new InputStreamReader(myCon.getInputStream(),"utf-8");
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = buffReader.readLine();
                //convert output string to JSON
                JSONObject jsonObj = new JSONObject(line);
                //test in console
                System.out.println("Status: "  + jsonObj.optString("status"));
                System.out.println("message: "  + jsonObj.optString("message"));
                //extract data from response json and set it in our reply model
                reply.setStatus(jsonObj.optString("status"));
                reply.setMessage(jsonObj.optString("message"));
                reply.setGroceryListName(jsonObj.optString("grocery_list_name"));
                reply.setPrivacy(jsonObj.optBoolean("privacy"));
                reply.setGroceryList(jsonObj.optJSONArray("list_items"));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return reply; //return login reply message model
    }



    public RouteModels.RemoveGroceryListReply removeGroceryListItem(EditGroceryList _model){
        RouteModels.RemoveGroceryListReply reply = new RouteModels.RemoveGroceryListReply();
        try{
            String loginEndpoint = "grocerylist/remove";
            URL url = new URL(this._baseUrl.toString() + loginEndpoint);
            HttpsURLConnection myCon = (HttpsURLConnection) url.openConnection();
            myCon.setRequestMethod("POST");
            myCon.setRequestProperty("Content-Type", "application/json");
            OutputStream body = myCon.getOutputStream();
            OutputStreamWriter bodyWriter = new OutputStreamWriter(body, "UTF-8");
            bodyWriter.write(_model.getJson()); //write  model to post body
            bodyWriter.flush();
            bodyWriter.close();
            body.close();
            //send api request
            myCon.connect();
            // get api response
            try{
                InputStreamReader inputReader = new InputStreamReader(myCon.getInputStream(),"utf-8");
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = buffReader.readLine();
                //convert output string to JSON
                JSONObject jsonObj = new JSONObject(line);
                //test in console
                System.out.println("Status: "  + jsonObj.optString("status"));
                System.out.println("message: "  + jsonObj.optString("message"));
                //extract data from response json and set it in our reply model
                reply.setStatus(jsonObj.optString("status"));
                reply.setMessage(jsonObj.optString("message"));
                reply.setGroceryListName(jsonObj.optString("grocery_list_name"));
                reply.setPrivacy(jsonObj.optBoolean("privacy"));
                reply.setGroceryList(jsonObj.optJSONArray("list_items"));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return reply;
    }

    public ConfirmRegisterReply CreateGroceryList(GroceryList _model){
        ConfirmRegisterReply reply = new ConfirmRegisterReply();
        try{
            String loginEndpoint = "grocerylist/create";
            URL url = new URL(this._baseUrl.toString() + loginEndpoint);
            HttpsURLConnection myCon = (HttpsURLConnection) url.openConnection();
            myCon.setRequestMethod("POST");
            myCon.setRequestProperty("Content-Type", "application/json");
            OutputStream body = myCon.getOutputStream();
            OutputStreamWriter bodyWriter = new OutputStreamWriter(body, "UTF-8");
            bodyWriter.write(_model.getJson()); //write  model to post body
            bodyWriter.flush();
            bodyWriter.close();
            body.close();
            //send api request
            myCon.connect();
            // get api response
            try{
                InputStreamReader inputReader = new InputStreamReader(myCon.getInputStream(),"utf-8");
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = buffReader.readLine();
                //convert output string to JSON
                JSONObject jsonObj = new JSONObject(line);
                //test in console
                System.out.println("Status: "  + jsonObj.optString("status"));
                System.out.println("message: "  + jsonObj.optString("message"));
                //extract data from response json and set it in our reply model
                reply.setStatus(jsonObj.optString("status"));
                reply.setMessage(jsonObj.optString("message"));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return reply;
    }

    public ConfirmRegisterReply DeleteGroceryList(GroceryList _model){
        ConfirmRegisterReply reply = new ConfirmRegisterReply();
        try{
            String loginEndpoint = "grocerylist/delete";
            URL url = new URL(this._baseUrl.toString() + loginEndpoint);
            HttpsURLConnection myCon = (HttpsURLConnection) url.openConnection();
            myCon.setRequestMethod("POST");
            myCon.setRequestProperty("Content-Type", "application/json");
            OutputStream body = myCon.getOutputStream();
            OutputStreamWriter bodyWriter = new OutputStreamWriter(body, "UTF-8");
            bodyWriter.write(_model.getJson()); //write  model to post body
            bodyWriter.flush();
            bodyWriter.close();
            body.close();
            //send api request
            myCon.connect();
            // get api response
            try{
                InputStreamReader inputReader = new InputStreamReader(myCon.getInputStream(),"utf-8");
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = buffReader.readLine();
                //convert output string to JSON
                JSONObject jsonObj = new JSONObject(line);
                //test in console
                System.out.println("Status: "  + jsonObj.optString("status"));
                System.out.println("message: "  + jsonObj.optString("message"));
                //extract data from response json and set it in our reply model
                reply.setStatus(jsonObj.optString("status"));
                reply.setMessage(jsonObj.optString("message"));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return reply;
    }

    public RouteModels.ViewGroceryListReply ViewGroceryList(GroceryList _model){
        RouteModels.ViewGroceryListReply reply = new RouteModels.ViewGroceryListReply();
        try{
            String loginEndpoint = "grocerylist/view";
            URL url = new URL(this._baseUrl.toString() + loginEndpoint);
            HttpsURLConnection myCon = (HttpsURLConnection) url.openConnection();
            myCon.setRequestMethod("POST");
            myCon.setRequestProperty("Content-Type", "application/json");
            OutputStream body = myCon.getOutputStream();
            OutputStreamWriter bodyWriter = new OutputStreamWriter(body, "UTF-8");
            bodyWriter.write(_model.getJson()); //write  model to post body
            bodyWriter.flush();
            bodyWriter.close();
            body.close();
            //send api request
            myCon.connect();
            // get api response
            try{
                InputStreamReader inputReader = new InputStreamReader(myCon.getInputStream(),"utf-8");
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = buffReader.readLine();
                //convert output string to JSON
                JSONObject jsonObj = new JSONObject(line);
                //test in console
                System.out.println("Status: "  + jsonObj.optString("status"));
                System.out.println("message: "  + jsonObj.optString("message"));
                //extract data from response json and set it in our reply model
                reply.setStatus(jsonObj.optString("status"));
                reply.setMessage(jsonObj.optString("message"));
                reply.setGroceryListName(jsonObj.optString("grocery_list_name"));
                reply.setPrivacy(jsonObj.optBoolean("privacy"));
                reply.setGroceryList(jsonObj.optJSONArray("list_items"));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return reply;
    }


}
