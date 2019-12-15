package com.example.mealbuddy.apitools;



import android.os.AsyncTask;

import com.example.mealbuddy.DayPlanGenerator;
import com.example.mealbuddy.advancedsearchFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SpoonacularApi {

    public static class SearchRecipeByName extends AsyncTask<String, Void, SearchData[]> {

        public interface SpoonacularReply {
            void processFinish(SpoonacularApi.SearchData[] output);
        }

        private SpoonacularReply delegate = null;


        public SearchRecipeByName(SpoonacularReply delegate){
            this.delegate = delegate;
        }
        protected void onPostExecute(SearchData[] result) {
            delegate.processFinish(result);
        }

        @Override
        protected SearchData[] doInBackground(String... name) {
            OkHttpClient client = new OkHttpClient();

            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("https")
                    .host("spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .addPathSegment("recipes")
                    .addPathSegment("search")
                    .addQueryParameter("query", name[0])
                    .build();

            // Call query
            Request request = new Request.Builder()
                    .url(httpUrl)
                    .get()
                    .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    //.addHeader("x-rapidapi-key", "1112002040mshedf4597e11fce17p17caf1jsn3139ade4941d") - James's API key
                    .addHeader("x-rapidapi-key", "ab88bb59c6mshac864d314171597p162324jsn506ae317be4b")
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e){
                e.printStackTrace();
            }

            String jsonData = null;
            try {
                jsonData = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject Jobject = null;
            try {
                Jobject = new JSONObject(jsonData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray jsonArray = null;
            try {
                jsonArray = Jobject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SearchData[] data = new SearchData[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    int id = object.getInt("id");
                    String title = object.getString("title");
                    String imageURL = object.getString("image");
                    SearchData newData = new SearchData(id,title,imageURL);
                    data[i] = newData;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return data;
        }
    }

    public static class SearchData {
        int id;
        String title;
        String imageURL;

        public SearchData(int id, String title, String imageURL){
            this.id = id;
            this.title = title;
            this.imageURL = imageURL;
        }

        public int getID(){
            return id;
        }

        public String getTitle(){ return title; }

        public String getImageURL(){
            return imageURL;
        }

    }

    public static class SearchRecipeById extends AsyncTask<String, Void, SearchDetailData> {

        public interface SpoonacularReply {
            void processFinish(SpoonacularApi.SearchDetailData output);
        }

        private SpoonacularReply delegate = null;


        public SearchRecipeById(SpoonacularReply delegate){
            this.delegate = delegate;
        }
        protected void onPostExecute(SearchDetailData result) {
            delegate.processFinish(result);
        }

        @Override
        protected SearchDetailData doInBackground(String... recipeId) {
            OkHttpClient client = new OkHttpClient();

            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("https")
                    .host("spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .addPathSegment("recipes")
                    .addPathSegment(recipeId[0])
                    .addPathSegment("information")
                    .build();

            // Call query
            Request request = new Request.Builder()
                    .url(httpUrl)
                    .get()
                    .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "ab88bb59c6mshac864d314171597p162324jsn506ae317be4b")
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e){
                e.printStackTrace();
            }

            String jsonData = null;
            try {
                jsonData = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject object = null;
            try {
                object = new JSONObject(jsonData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String title = null;
            String imageURL = null;
            String instructions = null;
            String readyInMinutes = null;

            try {
                title = object.getString("title");
                imageURL = object.getString("image");
                instructions = object.getString("instructions");
                readyInMinutes = object.getString("readyInMinutes");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SearchDetailData newData = new SearchDetailData(title,imageURL,instructions,readyInMinutes);
            return newData;
        }
    }

    public static class SearchDetailData {
        String title;
        String imageURL;
        String instructions;
        String readyInMinutes;

        public SearchDetailData(String title, String imageURL, String instructions,String readyInMinutes){
            this.title = title;
            this.imageURL = imageURL;
            this.instructions = instructions;
            this.readyInMinutes = readyInMinutes;
        }


        public String getTitle(){ return title; }

        public String getImageURL(){
            return imageURL;
        }

        public String getInstructions() { return instructions; }

        public String getReadyInMinutes(){ return readyInMinutes; }
    }

    public static class SearchIngredientById extends AsyncTask<String, Void, SearchIngredient[]> {

        public interface SpoonacularReply {
            void processFinish(SearchIngredient[] output);
        }

        private SpoonacularReply delegate = null;


        public SearchIngredientById(SpoonacularReply delegate){
            this.delegate = delegate;
        }
        protected void onPostExecute(SearchIngredient[] result) {
            delegate.processFinish(result);
        }

        @Override
        protected SearchIngredient[] doInBackground(String... recipeId) {
            OkHttpClient client = new OkHttpClient();

            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("https")
                    .host("spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .addPathSegment("recipes")
                    .addPathSegment(recipeId[0])
                    .addPathSegment("ingredientWidget.json")
                    .build();

            // Call query
            Request request = new Request.Builder()
                    .url(httpUrl)
                    .get()
                    .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "1112002040mshedf4597e11fce17p17caf1jsn3139ade4941d")
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e){
                e.printStackTrace();
            }

            String jsonData = null;
            try {
                jsonData = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject Jobject = null;
            try {
                Jobject = new JSONObject(jsonData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray jsonArray = null;
            try {
                jsonArray = Jobject.getJSONArray("ingredients");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SearchIngredient[] data = new SearchIngredient[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String imageURL = object.getString("image");
                    String ingredientName = object.getString("name");
                    JSONObject amountObj = object.getJSONObject("amount");
                    JSONObject metricObj = amountObj.getJSONObject("metric");
                    String amount = String.valueOf(metricObj.getDouble("value"));

                    amount += " " + metricObj.getString("unit");

                    SearchIngredient newData = new SearchIngredient(imageURL,ingredientName,amount);
                    data[i] = newData;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return data;
        }
    }

    public static class SearchIngredient {
        String imageURL;
        String ingredientName;
        String amount;

        public SearchIngredient(String imageURL, String ingredientName,String amount){
            this.imageURL = imageURL;
            this.ingredientName = ingredientName;
            this.amount = amount;
        }

        public String getImageURL(){
            return imageURL;
        }

        public String getIngredientName() { return ingredientName; }

        public String getAmount(){ return amount; }
    }

    public static class SearchRecipeByIngredient extends AsyncTask<advancedsearchFragment.RecipeSearchByIngredientParam, Void, SearchData[]> {

        public interface SpoonacularReply {
            void processFinish(SpoonacularApi.SearchData[] output);
        }

        private SpoonacularReply delegate = null;


        public SearchRecipeByIngredient(SpoonacularReply delegate){
            this.delegate = delegate;
        }
        protected void onPostExecute(SearchData[] result) {
            delegate.processFinish(result);
        }

        @Override
        protected SearchData[] doInBackground(advancedsearchFragment.RecipeSearchByIngredientParam... param) {
            OkHttpClient client = new OkHttpClient();

            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("https")
                    .host("spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .addPathSegment("recipes")
                    .addPathSegment("findByIngredients")
                    .addQueryParameter("ignorePantry", String.valueOf(param[0].isPantryChecked()))
                    .addQueryParameter("ingredients", param[0].getIngredients())
                    .build();

            System.out.println("My http url: " + httpUrl);
            // Call query
            Request request = new Request.Builder()
                    .url(httpUrl)
                    .get()
                    .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "1112002040mshedf4597e11fce17p17caf1jsn3139ade4941d")
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e){
                e.printStackTrace();
            }

            String jsonData = null;
            try {
                jsonData = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(jsonData);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SearchData[] data = new SearchData[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    int id = object.getInt("id");
                    String title = object.getString("title");
                    String imageURL = object.getString("image");
                    SearchData newData = new SearchData(id,title,imageURL);
                    data[i] = newData;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return data;
        }
    }

    public static class SearchAdvancedRecipe extends AsyncTask<advancedsearchFragment.RecipeAdvancedSearchParam, Void, SearchData[]> {

        public interface SpoonacularReply {
            void processFinish(SpoonacularApi.SearchData[] output);
        }

        private SpoonacularReply delegate = null;


        public SearchAdvancedRecipe(SpoonacularReply delegate) {
            this.delegate = delegate;
        }

        protected void onPostExecute(SearchData[] result) {
            delegate.processFinish(result);
        }

        @Override
        protected SearchData[] doInBackground(advancedsearchFragment.RecipeAdvancedSearchParam... param) {
            OkHttpClient client = new OkHttpClient();

            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("https")
                    .host("spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .addPathSegment("recipes")
                    .addPathSegment("complexSearch")
                    .addQueryParameter("offset", String.valueOf(0))
                    .addQueryParameter("number", String.valueOf(10))
                    .addQueryParameter("query", param[0].getRecipeName())
                    .addQueryParameter("cuisine", param[0].getCusine())
                    .addQueryParameter("includeIngredients", param[0].getIncludeIngredient())
                    .addQueryParameter("excludeIngredients", param[0].getExcludeIngerdient())
                    .addQueryParameter("intolerances", param[0].getIntolerance())
                    .addQueryParameter("minCalories", String.valueOf(param[0].getMinCal()))
                    .addQueryParameter("maxCalories", String.valueOf(param[0].getMaxCal()))
                    .addQueryParameter("minFat", String.valueOf(param[0].getMinFat()))
                    .addQueryParameter("maxFat", String.valueOf(param[0].getMaxFat()))
                    .addQueryParameter("minProtein", String.valueOf(param[0].getMinProtein()))
                    .addQueryParameter("maxProtein", String.valueOf(param[0].getMaxProtein()))
                    .addQueryParameter("minCarbs", String.valueOf(param[0].getMinCarbs()))
                    .addQueryParameter("maxCarbs", String.valueOf(param[0].getMaxCarbs()))
                    .addQueryParameter("minCaffeine", String.valueOf(param[0].getMinCaff()))
                    .addQueryParameter("maxCaffeine", String.valueOf(param[0].getMaxCaff()))
                    .addQueryParameter("minAlcohol", String.valueOf(param[0].getMinAlc()))
                    .addQueryParameter("maxAlcohol", String.valueOf(param[0].getMaxAlc()))
                    .addQueryParameter("minCalcium", String.valueOf(param[0].getMinCalcium()))
                    .addQueryParameter("maxCalcium", String.valueOf(param[0].getMaxCalcium()))
                    .addQueryParameter("minCholesterol", String.valueOf(param[0].getMinChol()))
                    .addQueryParameter("maxCholesterol", String.valueOf(param[0].getMaxChol()))
                    .addQueryParameter("minVitaminA", String.valueOf(param[0].getMinVitA()))
                    .addQueryParameter("maxVitaminA", String.valueOf(param[0].getMaxVitA()))
                    .addQueryParameter("minVitaminC", String.valueOf(param[0].getMinVitC()))
                    .addQueryParameter("maxVitaminC", String.valueOf(param[0].getMaxVitC()))
                    .addQueryParameter("minVitaminD", String.valueOf(param[0].getMinVitD()))
                    .addQueryParameter("maxVitaminD", String.valueOf(param[0].getMaxVitD()))
                    .addQueryParameter("minVitaminE", String.valueOf(param[0].getMinVitE()))
                    .addQueryParameter("maxVitaminE", String.valueOf(param[0].getMaxVitE()))
                    .addQueryParameter("minVitaminK", String.valueOf(param[0].getMinVitK()))
                    .addQueryParameter("maxVitaminK", String.valueOf(param[0].getMaxVitK()))
                    .addQueryParameter("limitLicense", String.valueOf(false))
                    .build();

            // Call query
            Request request = new Request.Builder()
                    .url(httpUrl)
                    .get()
                    .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "1112002040mshedf4597e11fce17p17caf1jsn3139ade4941d")
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonData = null;
            try {
                jsonData = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject Jobject = null;
            try {
                Jobject = new JSONObject(jsonData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray jsonArray = null;
            try {
                jsonArray = Jobject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SearchData[] data = new SearchData[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    int id = object.getInt("id");
                    String title = object.getString("title");
                    String imageURL = object.getString("image");
                    SearchData newData = new SearchData(id, title, imageURL);
                    data[i] = newData;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return data;
        }
    }

    public static class GenerateMealplanByDay extends AsyncTask<DayPlanGenerator.MealPlanParam, Void, SearchData[]> {
        public interface SpoonacularReply {
            void processFinish(SpoonacularApi.SearchData[] output);
        }

        private GenerateMealplanByDay.SpoonacularReply delegate = null;


        public GenerateMealplanByDay(GenerateMealplanByDay.SpoonacularReply delegate){
            this.delegate = delegate;
        }
        protected void onPostExecute(SearchData[] result) {
            delegate.processFinish(result);
        }

        protected SearchData[] doInBackground(DayPlanGenerator.MealPlanParam... parametrs) {
            OkHttpClient client = new OkHttpClient();

            String strSeparator = ",";
            String[] translate = new String[parametrs[0].getExcludes().length];
            translate = parametrs[0].getExcludes();

            String str = "";
            for (int i = 0;i < parametrs[0].getExcludes().length; i++) {
                str = str+translate[i];
                // Do not append comma at the end of last element
                if(i<translate.length-1){
                    str = str+strSeparator;
                }
            }

            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("https")
                    .host("spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .addPathSegment("recipes")
                    .addPathSegment("mealplans")
                    .addPathSegment("generate")
                    .addQueryParameter("timeFrame", parametrs[0].getDay())
                    .addQueryParameter("targetCalories", parametrs[0].getCalory())
                    .addQueryParameter("diet", parametrs[0].getDiet())
                    .addQueryParameter("exclude", str)
                    .build();

            Request request = new Request.Builder()
                    .url(httpUrl)
                    .get()
                    .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    //.addHeader("x-rapidapi-key", "1112002040mshedf4597e11fce17p17caf1jsn3139ade4941d") - James's API key
                    .addHeader("x-rapidapi-key", "ab88bb59c6mshac864d314171597p162324jsn506ae317be4b")
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e){
                e.printStackTrace();
            }

            String jsonData = null;
            try {
                jsonData = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject Jobject = null;
            try {
                Jobject = new JSONObject(jsonData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray jsonArray = null;
            try {
                jsonArray = Jobject.getJSONArray("meals");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SearchData[] data = new SearchData[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    int id = object.getInt("id");
                    String title = object.getString("title");
                    String imageURL = object.getString("image");
                    SearchData newData = new SearchData(id,title,imageURL);
                    data[i] = newData;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return data;
        }
    }

}