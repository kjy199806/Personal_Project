package com.example.mealbuddy.apitools;

import android.os.AsyncTask;

import com.example.mealbuddy.ChangePassword;
import com.example.mealbuddy.DetailRecipe;
import com.example.mealbuddy.GroceryListAdapter;
import com.example.mealbuddy.MyIngredientAdapter;
import com.example.mealbuddy.grocerylistFragment;
import com.example.mealbuddy.VerifyPassword;
import com.example.mealbuddy.VerifyUsername;
import com.example.mealbuddy.apitools.model.RouteModels;
import com.example.mealbuddy.apitools.model.RouteModels.RegistrationReply;
import com.example.mealbuddy.apitools.model.RouteModels.Registration;
import com.example.mealbuddy.apitools.model.RouteModels.*;
import com.example.mealbuddy.apitools.model.RouteModels.LoginReply;
import com.example.mealbuddy.apitools.model.RouteModels.Login;
import com.example.mealbuddy.apitools.model.RouteModels.ForgotReply;
import com.example.mealbuddy.apitools.model.RouteModels.Forgot;
import com.example.mealbuddy.apitools.model.RouteModels.ResetPass;
import com.example.mealbuddy.apitools.model.RouteModels.ResetReply;
import com.example.mealbuddy.apitools.model.RouteModels.ConfirmRegister;
import com.example.mealbuddy.apitools.model.RouteModels.ConfirmRegisterReply;

import com.example.mealbuddy.MainActivity;
import com.example.mealbuddy.createrecipy;
import com.example.mealbuddy.homepageFragment;
import com.example.mealbuddy.signup;
import com.example.mealbuddy.login;
import com.example.mealbuddy.forgotPassword;
import com.example.mealbuddy.forgotUsername;


public class MealBuddyTasks {

   static public class RegistrationTask extends AsyncTask<Registration, Void, RegistrationReply> {
        MealBuddyApiTools mbTools = new MealBuddyApiTools();
        private signup activity;

        public RegistrationTask(signup _activity){

            super();
            this.activity = _activity;
        }

        @Override
        protected RegistrationReply doInBackground(Registration ... reg) {
            RegistrationReply reply = mbTools.registerUser(reg[0]);
            return reply;
        }

        protected void onPostExecute(RegistrationReply fromBackground){
            super.onPostExecute(fromBackground);
            this.activity.registrationReply(fromBackground);
        }
    }


    static public class PostRecipeTask extends AsyncTask<RouteModels.PostRecipe, Void, RouteModels.PostRecipeReply> {
        MealBuddyApiTools mbTools = new MealBuddyApiTools();
        private createrecipy activity;

        public PostRecipeTask(createrecipy _activity){
            super();
            this.activity = _activity;
        }

        @Override
        protected RouteModels.PostRecipeReply doInBackground(RouteModels.PostRecipe... reg) {
            RouteModels.PostRecipeReply reply = mbTools.postRecipe(reg[0]);
            return reply;
        }

        protected void onPostExecute(RouteModels.PostRecipeReply fromBackground){
            super.onPostExecute(fromBackground);
            this.activity.postRecipeReply(fromBackground);
        }
    }

    static public class GetRecipeById extends AsyncTask<RouteModels.GetRecipeById, Void, RouteModels.GetRecipeByIdReply> {
        MealBuddyApiTools mbTools = new MealBuddyApiTools();
        private homepageFragment activity;

        public GetRecipeById(homepageFragment _activity){
            super();
            this.activity = _activity;
        }

        @Override
        protected RouteModels.GetRecipeByIdReply doInBackground(RouteModels.GetRecipeById... reg) {
            RouteModels.GetRecipeByIdReply reply = mbTools.getRecipeById(reg[0]);
            return reply;
        }

        protected void onPostExecute(RouteModels.GetRecipeByIdReply fromBackground){
            super.onPostExecute(fromBackground);
            this.activity.getRecipeByIdReply(fromBackground);
        }
    }

    static public class GetRecipeById2 extends AsyncTask<RouteModels.GetRecipeById2, Void, RouteModels.GetRecipeByIdReply2> {
        MealBuddyApiTools mbTools = new MealBuddyApiTools();
        private DetailRecipe activity;

        public GetRecipeById2(DetailRecipe _activity){
            super();
            this.activity = _activity;
        }

        @Override
        protected RouteModels.GetRecipeByIdReply2 doInBackground(RouteModels.GetRecipeById2... reg) {
            RouteModels.GetRecipeByIdReply2 reply = mbTools.getRecipeById2(reg[0]);
            return reply;
        }

        protected void onPostExecute(RouteModels.GetRecipeByIdReply2 fromBackground){
            super.onPostExecute(fromBackground);
            this.activity.getRecipeByIdReply2(fromBackground);
        }
    }

    //async login task to run in background
    static public class LoginTask extends AsyncTask<Login, Void, LoginReply>{
        MealBuddyApiTools mbTools = new MealBuddyApiTools();
        private login activity;

        public LoginTask(login act){
            super();
            this.activity = act;
        }

        //run login reply function in background while passing in login model
        @Override
        protected LoginReply doInBackground(Login ... log) {
            LoginReply reply = mbTools.userLogin(log[0]);
            return reply;
        }

        protected void onPostExecute(LoginReply fromBackground){
            super.onPostExecute(fromBackground);
            this.activity.loginReply(fromBackground);
        }

    }

    //async forgot username task to run in background
    static public class ForgotUserTask extends AsyncTask<Forgot, Void, ForgotReply>{
        MealBuddyApiTools mbTools = new MealBuddyApiTools();
        private forgotUsername activity;

        public ForgotUserTask(forgotUsername act){
            super();
            this.activity = act;
        }
        @Override
        protected ForgotReply doInBackground(Forgot ... forgots) {
            ForgotReply reply = mbTools.forgotUserReply(forgots[0]);
            return reply;
        }
        protected void onPostExecute(ForgotReply fromBackground){
            super.onPostExecute(fromBackground);
            this.activity.forgotReply(fromBackground);
        }

    }

    //async forgot password task to run in background
    static public class ForgotPassTask extends AsyncTask<Forgot, Void, ForgotReply>{
        MealBuddyApiTools mbTools = new MealBuddyApiTools();
        private forgotPassword activity;

        public ForgotPassTask(forgotPassword act){
            super();
            this.activity = act;
        }
        @Override
        protected ForgotReply doInBackground(Forgot ... forgots) {
            ForgotReply reply = mbTools.forgotPassReply(forgots[0]);
            return reply;
        }
        protected void onPostExecute(ForgotReply fromBackground){
            super.onPostExecute(fromBackground);
            this.activity.forgotReply(fromBackground);
        }

    }
    //async check token task to run in background
    static public class CheckTokenTask extends AsyncTask<Forgot, Void, ForgotReply>{
        MealBuddyApiTools mbTools = new MealBuddyApiTools();
        private VerifyPassword activity;

        public CheckTokenTask(VerifyPassword act){
            super();
            this.activity = act;
        }
        @Override
        protected ForgotReply doInBackground(Forgot ... forgots) {
            ForgotReply reply = mbTools.checkToken(forgots[0]);
            return reply;
        }
        protected void onPostExecute(ForgotReply fromBackground){
            super.onPostExecute(fromBackground);
            this.activity.forgotReply(fromBackground);
        }

    }

    //async reset pass task to run in background
    static public class ResetTask extends AsyncTask<ResetPass, Void, ResetReply>{
        MealBuddyApiTools mbTools = new MealBuddyApiTools();
        private ChangePassword activity;

        public ResetTask(ChangePassword act){
            super();
            this.activity = act;
        }

        //run login reply function in background while passing in login model
        @Override
        protected ResetReply doInBackground(ResetPass ... rPass) {
            ResetReply reply = mbTools.resetPassword(rPass[0]);
            return reply;
        }

        protected void onPostExecute(ResetReply fromBackground){
            super.onPostExecute(fromBackground);
            this.activity.resetReply(fromBackground);
        }

    }

    static public class ConfirmRegisterTask extends AsyncTask<ConfirmRegister, Void, ConfirmRegisterReply>{
        MealBuddyApiTools mbTools = new MealBuddyApiTools();
        private VerifyUsername activity;

        public ConfirmRegisterTask(VerifyUsername act){
            super();
            this.activity = act;
        }

        //run login reply function in background while passing in login model
        @Override
        protected ConfirmRegisterReply doInBackground(ConfirmRegister ... cRegister) {
            ConfirmRegisterReply reply = mbTools.checkRToken(cRegister[0]);
            return reply;
        }

        protected void onPostExecute(ConfirmRegisterReply fromBackground){
            super.onPostExecute(fromBackground);
            this.activity.confirmRegisterReply(fromBackground);
        }

    }

    static public class AddGroceryListItemTask extends AsyncTask<EditGroceryList, Void, AddGroceryListReply>{
        MealBuddyApiTools mbTools = new MealBuddyApiTools();
        private MyIngredientAdapter activity;

        public AddGroceryListItemTask(MyIngredientAdapter act){
            super();                      //put activity that is calling this task
            this.activity = act;
        }


        @Override
        protected AddGroceryListReply doInBackground(EditGroceryList ... gList) {
            AddGroceryListReply reply = mbTools.addGroceryListItem(gList[0]);
            return reply;
        }

        protected void onPostExecute(AddGroceryListReply fromBackground){
            super.onPostExecute(fromBackground);
            this.activity.addGroceryListReply(fromBackground);
        }

    }

    static public class RemoveGroceryListItemTask extends AsyncTask<EditGroceryList, Void, RemoveGroceryListReply>{
        MealBuddyApiTools mbTools = new MealBuddyApiTools();
        private GroceryListAdapter activity;

        public RemoveGroceryListItemTask(GroceryListAdapter act){
            super();                      //put activity that is calling this task
            this.activity = act;
        }


        @Override
        protected RemoveGroceryListReply doInBackground(EditGroceryList ... gList) {
           RemoveGroceryListReply reply = mbTools.removeGroceryListItem(gList[0]);
            return reply;
        }

        protected void onPostExecute(RemoveGroceryListReply fromBackground){
            super.onPostExecute(fromBackground);
            this.activity.removeGroceryListReply(fromBackground);
        }

    }

    static public class CreateGroceryListTask extends AsyncTask<GroceryList, Void, ConfirmRegisterReply>{
        MealBuddyApiTools mbTools = new MealBuddyApiTools();
        private VerifyUsername activity;

        public CreateGroceryListTask(VerifyUsername act){
            super();                      //put activity that is calling this task
            this.activity = act;
        }


        @Override
        protected ConfirmRegisterReply doInBackground(GroceryList ... gList) {
            ConfirmRegisterReply reply = mbTools.CreateGroceryList(gList[0]);
            return reply;
        }

        protected void onPostExecute(ConfirmRegisterReply fromBackground){
            super.onPostExecute(fromBackground);
            this.activity.confirmRegisterReply(fromBackground);
        }

    }

    static public class DeleteGroceryListTask extends AsyncTask<GroceryList, Void, ConfirmRegisterReply>{
        MealBuddyApiTools mbTools = new MealBuddyApiTools();
        private VerifyUsername activity;

        public DeleteGroceryListTask(VerifyUsername act){
            super();                      //put activity that is calling this task
            this.activity = act;
        }


        @Override
        protected ConfirmRegisterReply doInBackground(GroceryList ... gList) {
            ConfirmRegisterReply reply = mbTools.DeleteGroceryList(gList[0]);
            return reply;
        }

        protected void onPostExecute(ConfirmRegisterReply fromBackground){
            super.onPostExecute(fromBackground);
            this.activity.confirmRegisterReply(fromBackground);
        }

    }

    static public class ViewGroceryListTask extends AsyncTask<GroceryList, Void, ViewGroceryListReply>{
        MealBuddyApiTools mbTools = new MealBuddyApiTools();
        private grocerylistFragment activity;

        public ViewGroceryListTask(grocerylistFragment act){
            super();                      //put activity that is calling this task
            this.activity = act;
        }


        @Override
        protected ViewGroceryListReply doInBackground(GroceryList ... gList) {
            ViewGroceryListReply reply = mbTools.ViewGroceryList(gList[0]);
            return reply;
        }

        protected void onPostExecute(ViewGroceryListReply fromBackground){
            super.onPostExecute(fromBackground);
            this.activity.viewGroceryListReply(fromBackground); //add reply to view
        }

    }

}
