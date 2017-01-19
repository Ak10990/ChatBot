package com.hiq.freedomvision.helpers;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.hiq.freedomvision.apis.RetrofitApiClient;
import com.hiq.freedomvision.apis.RetrofitApiService;
import com.hiq.freedomvision.flows.OptionsFragment;
import com.hiq.freedomvision.models.DestinationResult;
import com.hiq.freedomvision.models.LeadFormResult;
import com.hiq.freedomvision.models.MainObjectResult;
import com.hiq.freedomvision.models.QueParse;
import com.hiq.freedomvision.models.UserChatObject;
import com.hiq.freedomvision.models.WeatherResult;
import com.hiq.freedomvision.utils.NetworkUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by akanksha on 21/10/16.
 */

public class QueryEngine {

    private Context context;
    //    private List<EngineHelper> engineHelperlist;
    private static UserChatObject userChatObj;
    private Handler handler = new Handler();
    private ActivityEngineListener listener;

    private final String beginConversation = "Hi, I am your holiday planner.";
    private final String noInternetMessage = "Need to call our specialized 007 agent. Please check that you have an active internet connection.";
    private final String byeMessage = "Hope to hear from you soon.";

    private static final String a1 = "Plan a holiday";
    private static final String a2 = "Heard you are planning a holiday. So, where you are headed to?";
    private static final String a3 = "Sorry didn't get you there. Where are you planning your holiday to?";//trip->holiday todo
    private static final String a4 = "Are you looking for Destination ";
    private static final String a5 = "There's so much to know about! From hotels, packages, weather, places to visit.. I've got it all covered. What do you want to do?";
//    public static final String a6 = "Still deciding on your holiday. Let me help you more.";

    private static final String b1 = "Want to know more about a destination.";
    private static final String b2 = "Heard you have planned a holiday. So, where you are headed to?";
    private static final String b3 = "Hotels in ";
    //    private static final String b4 = "Places to visit in ";
    private static final String b5 = "Weather info about ";

    private static final String b11 = "Gotcha! Here's what I got.";

    private static final String c1 = "Need an inspiration to travel";
    private static final String c2 = "What is your origin city?";
    private static final String c3 = "How many are planning to go?";
    private static final String c4 = "A solo holiday";
    public static final String c5 = "Couple holiday";
    public static final String c6 = "Family holiday";
    private static final String c7 = "Friends Group";
    private static final String c8 = "Can you help me more on the number of travel days";
    private static final String c9 = "Weekend";
    private static final String c10 = "1 week";
    private static final String c11 = "> 1 week";
    private static final String c12 = "What's your budget";
    private static final String c13 = "<Rs 2500";
    private static final String c14 = "Rs 2500 - Rs 5000";
    private static final String c15 = "Rs 5000- Rs 10000";
    private static final String c16 = "> Rs 10000";
    private static final String c17 = "Oh! I couldn't catch your name, please share";
    private static final String c18 = "I have some cool things to share with you, share your Email";
    private static final String c19 = "Please share your number, I will provide all information about.";

    private static final String d1 = "I don't like travelling";
    private static final String d2 = "Here's your chance to refer and earn. You can refer my service to your friend, I would help them with my wisdom.";

    private static final String e1 = "Are you satisfied with my results?";
    private static final String e2 = "EXCELLENT :)";
    private static final String e3 = "OKAY :|";
    private static final String e4 = "TERRIBLE :/";

    public static HashMap<String, String> hotel = new HashMap<>();

    //    public static final List<String> objectWords=Arrays.asList("hotel","destination","sightseeing","weather","packages");
    private static final String defaultHead = "Let\'s explore";
   /* public static final List<String> aQue = Arrays.asList(
            "Greetings! I answer all your holiday queries related to hotels, places to visit, weather!",
            "Hello! Heard you're planning a trip? Need some answers?",
            "Hi, I've been gathering lots of travel information to share with you.",
            "Hey, I'm your personal assistant to help you plan a memorable holiday."
    );

    private static final List<String> cQue = Arrays.asList("What sounds like an ideal holiday for you?");
    private static final List<String> cAns = Arrays.asList("Holiday types", "" + typeOptionsDialog,
            "Hills", "Beaches", "Adventure")*/;//TODO

    public QueryEngine(Context context, ActivityEngineListener listener) {
        this.context = context;
        this.listener = listener;
        userChatObj = new UserChatObject();

        /*engineHelperlist = new ArrayList<>();

        HashMap<String, String> map = new HashMap<>();
        map.put(a1, a2);
        map.put(b1, b2);
        map.put(c1, c3);
        map.put(d1, d2);

        EngineHelper engineHelper = new EngineHelper(beginConversation, map);
        engineHelperlist.add(engineHelper);

        map.clear();
        map.put(a2, a2);
//        engineHelper = new EngineHelper(c3, map);
//        engineHelperlist.add(engineHelper);


        map.clear();
        map.put(c4, null);
        map.put(c5, null);
        map.put(c6, null);
        map.put(c7, null);
        engineHelper = new EngineHelper(c3, map);
        engineHelperlist.add(engineHelper);

        map.put(c9, null);
        map.put(c10, null);
        map.put(c11, null);
        engineHelper = new EngineHelper(c8, map);
        engineHelperlist.add(engineHelper);*/
    }

    public String sendQuery(String question, String answer, boolean isGoodBye) {
        if (question == null && answer == null) {
            receiveQuery(new QueParse(beginConversation, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_YN,
                    Arrays.asList(a1, b1, c1, d1)));
            return null;
        } else if (question.equals(a2) || question.equals(a3) || question.equals(b2)) {
            userChatObj.setDestName(answer);
            sendToServer("Destination", answer);
            return "Planning a holiday for " + userChatObj.getDestName();
        } else if (question.equals(c2)) {
            userChatObj.setOriginCity(answer);//TODO check
            receiveQuery(new QueParse(c3, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_YN,
                    Arrays.asList(c4, c5, c6, c7)));
            return answer;
        } else if (question.equals(b11) || question.startsWith("Weather results for")) {
            receiveQuery(new QueParse(c2, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_NONE, null));
            return answer;
        } else if (question.equals(e1)) {
            if (isGoodBye) {
                startBye();
            } else {
                //TODO
            }
            return answer;
        } else if (question.startsWith(a4)) {
            if (answer.equals("YES")) {
                receiveQuery(new QueParse(a5, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_YN,
                        Arrays.asList(b3 + userChatObj.getDestName(), /*b4 + userChatObj.getDestName(),*/ b5 + userChatObj.getDestName())));
            } else if (answer.equals("NO")) {
                receiveQuery(new QueParse(a3, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_NONE, null));
            }
            return answer;
        } else if (answer.startsWith(b3)) {
            sendToServer("Hotel", answer);
            return answer;
        } else if (answer.startsWith(b5)) {
            sendToServer("Weather", answer);
            return answer;
        } else if (answer.equals(a1)) {
            userChatObj.setUserType(1);
            receiveQuery(new QueParse(a2, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_NONE, null));
            return answer;
        } else if (answer.equals(b1)) {
            userChatObj.setUserType(2);
            receiveQuery(new QueParse(b2, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_NONE, null));
            return null;
        } else if (answer.equals(c1)) {
            userChatObj.setUserType(3);
            receiveQuery(new QueParse(c2, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_NONE, null));
            return answer;
        } else if (answer.equals(c4) || answer.equals(c5) || answer.equals(c6) || answer.equals(c7) || question.equals(c3)) {
            switch (answer) {
                case c4:
                    userChatObj.setNoOfPeople("" + 1);
                    break;
                case c5:
                    userChatObj.setNoOfPeople("" + 2);
                    break;
                case c6:
                    userChatObj.setNoOfPeople("" + 4);
                    break;
                case c7:
                    userChatObj.setNoOfPeople("" + 6);
                    break;
                default:
                    userChatObj.setNoOfPeople(answer);
                    break;
            }
            receiveQuery(new QueParse(c8, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_YN,
                    Arrays.asList(c9, c10, c11)));
            return answer;
        } else if (answer.equals(c9) || answer.equals(c10) || answer.equals(c11) || question.equals(c8)) {
            switch (answer) {
                case c9:
                    userChatObj.setNoOfNights("" + 2);
                    break;
                case c10:
                    userChatObj.setNoOfNights("" + 6);
                    break;
                case c11:
                    userChatObj.setNoOfNights("" + 10);
                    break;
                default:
                    userChatObj.setNoOfNights(answer);
                    break;
            }
            receiveQuery(new QueParse(c12, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_YN,
                    Arrays.asList(c13, c14, c15, c16)));
            return answer;
        } else if (answer.equals(c13) || answer.equals(c14) || answer.equals(c15) || answer.equals(c16) || question.equals(c12)) {
            userChatObj.setPriceRange(answer);
            if (userChatObj.getUserType() == 3) {
                userChatObj.setDestName("Bangalore");//TODO hardcoded
            }
            receiveQuery(new QueParse(c17, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_NONE, null));
            return answer;
        } else if (question.equals(c17)) {
            userChatObj.setUsername(answer);
            receiveQuery(new QueParse(c18, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_NONE, null));
            return answer;
        } else if (question.equals(c18)) {
            userChatObj.setEmail(answer);
            receiveQuery(new QueParse(c19, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_NONE, null));
            return new String(new char[answer.length()]).replace('\0', '*');
        } else if (question.equals(c19)) {
            userChatObj.setMobile(answer);
            sendForm();
            return new String(new char[answer.length()]).replace('\0', '*');
        } else if (answer.equals(d1)) {
            userChatObj.setUserType(4);
            receiveQuery(new QueParse(d2, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_NONE, null));
            return answer;
        } else if (answer.equals("RATING")) {
            startRating();
            return null;
        } else if (answer.equals("BYE")) {
            startBye();
            return answer;
        } else {
            if ((answer.toLowerCase()).contains("hotel")) {
                sendToServer("Hotel", answer);
            } else if ((answer.toLowerCase()).contains("Destination")) {
                sendToServer("Destination", answer);
            } else if ((answer.toLowerCase()).contains("Weather")) {
                sendToServer("Weather", answer);
            } else {
                sendToServer("Random", answer);
            }
            return answer;
        }
    }

    private void startBye() {
        receiveQuery(new QueParse(byeMessage, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_NONE, null));
    }

    private void startRating() {
        receiveQuery(new QueParse(e1, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_YN,
                Arrays.asList(e2, e3, e4)));
    }

    private void sendForm() {
        if (NetworkUtils.isInternetConnected(context)) {
            RetrofitApiClient retrofitApiClient = new RetrofitApiClient();
            RetrofitApiService apiService = retrofitApiClient.getApiService();
            final Call<LeadFormResult> call = apiService.listLeadFormResponse(userChatObj.getUsername(), userChatObj.getEmail(), userChatObj.getMobile(),
                    userChatObj.getOriginCity(), userChatObj.getNoOfPeople(), userChatObj.getNoOfNights(),
                    userChatObj.getPriceRange(), userChatObj.getDestName());
            if (call != null) {
                call.enqueue(new Callback<LeadFormResult>() {
                    @Override
                    public void onResponse(Call<LeadFormResult> call, Response<LeadFormResult> response) {
                        String toast;
                        if (response != null && response.body() != null && response.body().getData() != null
                                && response.body().getData().getStatus() != null) {
                            String status = response.body().getData().getStatus();
                            if (Integer.parseInt(status) == 1) {
                                toast = "Successful submission of your results. Our agent will get back to you in a short while.";
                            } else {
                                toast = "Sorry, Could not submit your query successfully.";
                            }
                        } else {
                            Log.e("Error in API", "No Results Found");
                            toast = "No Results Found.";
                        }
                        receiveQuery(new QueParse(toast, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_NONE, null));
                    }

                    @Override
                    public void onFailure(Call<LeadFormResult> call, Throwable t) {
                        Log.e("Error in API", "" + t.getMessage());
                    }
                });
            }
        } else {
            receiveQuery(new QueParse(noInternetMessage, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_NONE, null));
        }
    }

    private void sendToServer(final String type, final String answer) {
        if (NetworkUtils.isInternetConnected(context)) {
            if (type.equals("Destination") || type.equals("Random")) {
                RetrofitApiClient retrofitApiClient = new RetrofitApiClient();
                RetrofitApiService apiService = retrofitApiClient.getApiService();
                final Call<DestinationResult> call = apiService.listDestinationResponse(answer);
                if (call != null) {
                    call.enqueue(new Callback<DestinationResult>() {
                        @Override
                        public void onResponse(Call<DestinationResult> call, Response<DestinationResult> response) {
                            if (response != null && response.body() != null && response.body().getType() != null) {
                                String type = response.body().getType();
                                if (type.equals("only_destination_data")) {
                                    receiveQuery(new QueParse(a4 + answer, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_YN,
                                            Arrays.asList("YES", "NO")));
                                } else {
                                    receiveQuery(new QueParse(a3, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_NONE, null));
                                }

                            } else {
                                Log.e("Error in API", "No Results Found");
                            }
                        }

                        @Override
                        public void onFailure(Call<DestinationResult> call, Throwable t) {
                            Log.e("Error in API", "" + t.getMessage());
                        }
                    });
                }
            } else if (type.equals("Hotel")) {
                RetrofitApiClient retrofitApiClient = new RetrofitApiClient();
                RetrofitApiService apiService = retrofitApiClient.getApiService();
                final Call<MainObjectResult> call = apiService.listMainObjectResponse(answer.toLowerCase());
                if (call != null) {
                    call.enqueue(new Callback<MainObjectResult>() {
                        @Override
                        public void onResponse(Call<MainObjectResult> call, Response<MainObjectResult> response) {
                            if (response != null && response.body() != null && response.body().getData().length > 0) {
                                MainObjectResult.Data[] dataResult = response.body().getData();
                                receiveQuery(new QueParse(b11, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_YN,
                                        Arrays.asList(dataResult[0].getHotel_name() + "!!!!!" + "http://www.holidayiq.com/Savoy-Hotel-Ooty-hotel-" + dataResult[0].getHotel_id() + ".html",//TODO latlng
                                                dataResult[1].getHotel_name() + "!!!!!" + "http://www.holidayiq.com/Savoy-Hotel-Ooty-hotel-" + dataResult[1].getHotel_display_name() + ".html",
                                                dataResult[2].getHotel_name() + "!!!!!" + "http://www.holidayiq.com/Savoy-Hotel-Ooty-hotel-" + dataResult[2].getHotel_display_name() + ".html")));
                            } else {
                                Log.e("Error in API", "No Results Found");
                            }
                        }

                        @Override
                        public void onFailure(Call<MainObjectResult> call, Throwable t) {
                            Log.e("Error in API", "" + t.getMessage());
                        }
                    });
                }
            } else if (type.equals("Weather")) {
                RetrofitApiClient retrofitApiClient = new RetrofitApiClient();
                RetrofitApiService apiService = retrofitApiClient.getApiService();
                final Call<WeatherResult> call = apiService.listWeatherResponse(answer.toLowerCase());
                if (call != null) {
                    call.enqueue(new Callback<WeatherResult>() {
                        @Override
                        public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                            if (response != null && response.body() != null && response.body().getData() != null) {
                                WeatherResult.Data dataResult = response.body().getData();
                                receiveQuery(new QueParse(
                                        "Weather results for " + userChatObj.getDestName() + " is " + dataResult.getTemperature() + (char) 0x00B0 + "C",
                                        defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_NONE, null));
                                sendQuery("Weather results for ", "", false);

                            } else {
                                Log.e("Error in API", "No Results Found");
                            }
                        }

                        @Override
                        public void onFailure(Call<WeatherResult> call, Throwable t) {
                            Log.e("Error in API", "" + t.getMessage());
                        }
                    });
                }
            }
        } else {
            receiveQuery(new QueParse(noInternetMessage, defaultHead, OptionsFragment.ALL_DIALOGS.OPTION_NONE, null));
        }
    }

    private void receiveQuery(final QueParse queParse) {
        handler.postDelayed(new TimerTask() {
            @Override
            public void run() {
                listener.onReceiveQuery(queParse);
            }
        }, 1000);
    }

    public interface ActivityEngineListener {
        void onReceiveQuery(QueParse queParse);
    }

}
