package app.me.nightfall.lobby;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import app.me.nightfall.R;
import app.me.nightfall.home.MainActivity;

public class LobbiesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public List<LobbyPostModel> lobbyList;
    public Context context;
    private String userID;
    public MainActivity mainActivity;
    public static String lobbyID;
    private ImageView hostIcon;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    public static Integer playerPos = 0;



    public LobbiesRecyclerAdapter(List<LobbyPostModel> lobbyList){
        this.lobbyList = lobbyList;
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userID = firebaseAuth.getCurrentUser().getUid();
    }

    private class VIEW_TYPES {
        public static final int Normal = 1;
        public static final int Ads = 2;
    }



    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPES.Normal;
    }

    @NonNull
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        View MainView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lobby_recycler_card, parent, false);


        if (viewType == VIEW_TYPES.Normal){
            return new ViewHolder0(MainView);
        }
        else{
            return new ViewHolder0(MainView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        switch(holder.getItemViewType()) {

            case VIEW_TYPES.Ads:
                ViewHolderAds viewHolderAds = (ViewHolderAds) holder;
                break;
            case VIEW_TYPES.Normal:
                ViewHolder0 viewHolder0 = (ViewHolder0) holder;

                final String title = lobbyList.get(position).getTitle();
                final String category = lobbyList.get(position).getCategory();
                Long tot_views = lobbyList.get(position).getTot_views();

                viewHolder0.setTitle(title);
                viewHolder0.setCategory(category);

                viewHolder0.joinBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        lobbyID = lobbyList.get(position).getLobbyID();

                            final ProgressDialog pd = new ProgressDialog(context, R.style.dialogTheme);
                            pd.setMessage("Joining campfire...");
                            pd.setCancelable(false);
                            pd.show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    db.collection("Users").document(userID).update("inLobby", lobbyID).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            db.collection("Lobbies").document(lobbyID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    Long tot_views = documentSnapshot.getLong("tot_views");
                                                    tot_views = tot_views + 1;

                                                    Long cur_views = documentSnapshot.getLong("cur_views");
                                                    cur_views = cur_views + 1;

                                                    db.collection("Lobbies").document(lobbyID).update("tot_views", tot_views);
                                                    db.collection("Lobbies").document(lobbyID).update("tot_views", cur_views);

                                                    MainActivity.inLobbyID = lobbyID;
                                                    Intent i1 = new Intent (context, LobbyActivity.class);
                                                    context.startActivity(i1);
                                                    pd.dismiss();

                                                }
                                            });


                                        }

                                    });

                                }
                            }, 1000);


                    }
                });



        }
    }

    @Override
    public int getItemCount() {
        return lobbyList.size();
    }

    public class ViewHolder0 extends RecyclerView.ViewHolder{

        private View mView;
        private ImageView joinBtn;

        public ViewHolder0(@NonNull View itemView) {

            super(itemView);
            mView = itemView;
            joinBtn = mView.findViewById(R.id.joinBtn);
        }

        public void setTitle(String text){
            TextView title_tv = mView.findViewById(R.id.username);
            title_tv.setText(text);

        }

        public void setCategory(String text){
            TextView category = mView.findViewById(R.id.post_category);
            category.setText(text);
        }

    }
    public class ViewHolderAds extends RecyclerView.ViewHolder{

        private View mView;

        public ViewHolderAds(@NonNull View itemView) {

            super(itemView);
            mView = itemView;

        }

    }

}
