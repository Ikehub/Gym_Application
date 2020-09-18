package com.example.gymapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String PLANS_KEY = "plans";
    private static final String TRAINING_KEY = "trainings";
//    private static ArrayList<Training> trainings;
//    private static ArrayList<Plan> plans;

    private SharedPreferences sharedPreferences;
    private static Utils instance;

    public static synchronized Utils getInstance(Context context) {
        if (instance == null) {
            instance = new Utils(context);
        }
        return instance;
    }

    private Utils(Context context) {
        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);

        if (getTrainings() == null) {
            initTrainings();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (getPlans() == null) {
            editor.putString(PLANS_KEY, gson.toJson(new ArrayList<Plan>()));
            editor.commit();
        }
    }



    public void initTrainings() {

        ArrayList<Training> trainings = new ArrayList<>();
        trainings = new ArrayList<>();

        Training pushUp = new Training(1, "Push Up", "A push-up is a common calisthenics exercise beginning from the prone position.",
                "The definition of a push-up is an exercise done laying with face, palms and toes facing down, keeping legs and back straight, extending arms straight to push body up and back down again.",
                "https://i.stack.imgur.com/caIab.jpg");
        trainings.add(pushUp);

        Training squat = new Training(2, "Squat", "A squat is a strength exercise in which the trainee lowers their hips from a standing position and then stands back up.",
                "Squats are considered a vital exercise for increasing the strength and size of the lower body muscles as well as developing core strength.",
                "https://blogs.rdxsports.com/wp-content/uploads/2017/08/Squat.jpg");
        trainings.add(squat);

        Training legPress = new Training(3, "Leg Press", "The leg press is a weight training exercise in which the individual pushes a weight or resistance away from them using their legs.",
                "The term leg press also refers to the apparatus used to perform this exercise. The leg press can be used to evaluate an athlete's overall lower body strength (from knee joint to hip).",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Muscle_Strengthening_at_the_Gym_-_Seated_Leg_Press.webm/320px--Muscle_Strengthening_at_the_Gym_-_Seated_Leg_Press.webm.jpg");
        trainings.add(legPress);

        Training pectorals = new Training(4, "Pectorals", "Amazing for building chest muscles",
                "Lie down on a flat bench with a dumbbell in each hand. Raise your arms shoulder-width apart with palms facing each other. With a slight bend in your elbows, lower your arms out at both sides in a wide arc until you feel a stretch in your chest. Bring your arms back up, squeezing your chest muscles.",
                "https://i.pinimg.com/236x/b9/7a/2e/b97a2e33c45b9cc68cfafc6e94298494.jpg");
        trainings.add(pectorals);

        Training pullUp = new Training(5, "Pull Up", "A pull-up is an upper-body strength exercise.",
                "The pull-up is a closed-chain movement where the body is suspended by the hands and pulls up. As this happens, the elbows flex and the shoulders adduct and extend to bring the elbows to the torso.",
                "https://www.climbing.com/.image/t_share/MTM3MzI0NDMzMjY5MzM1MjA5/trainingperfectpullupsjpg.jpg");
        trainings.add(pullUp);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(TRAINING_KEY, gson.toJson(trainings));
        editor.commit();

    }

    public ArrayList<Training> getTrainings() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Training>>(){}.getType();
        ArrayList<Training> trainings = gson.fromJson(sharedPreferences.getString(TRAINING_KEY, null), type);
        return trainings;
    }

    public boolean addPlan(Plan plan) {
        ArrayList<Plan> plans = getPlans();
        if (plans != null) {
            if (plans.add(plan)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(PLANS_KEY);
                editor.putString(PLANS_KEY, gson.toJson(plans));
                editor.commit();
                return true;
            }
        }

        return false;
    }

    public ArrayList<Plan> getPlans() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Plan>>(){}.getType();
        ArrayList<Plan> plans = gson.fromJson(sharedPreferences.getString(PLANS_KEY, null), type);
        return plans;
    }

    public boolean removePlan(Plan plan) {
        ArrayList<Plan> plans = getPlans();
        if (plans != null) {
            for (Plan p : plans) {
                if (p.getTraining().getId() == plan.getTraining().getId()) {
                    if (plans.remove(p)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(PLANS_KEY);
                        editor.putString(PLANS_KEY, gson.toJson(plans));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
