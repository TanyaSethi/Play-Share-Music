package androidapp.musicshare;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link groupDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class groupDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TabLayout tb ;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LinearLayout mLinearLayoutmember;
    LinearLayout mLinearLayoutsong;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<String> memberList = new ArrayList<>();
    ArrayList<String> songList = new ArrayList<>();
    int count ;
    int countsong;
    TextView gname;
    MediaPlayer mMediaplayer;
    Handler mHandler;
    Runnable mRunnable;
    public groupDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment groupDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static groupDetails newInstance(String param1, String param2) {
        groupDetails fragment = new groupDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_details, container, false);
        // Inflate the layout for this fragment
        mLinearLayoutmember = (LinearLayout) view.findViewById(R.id.memberLayout);
        mLinearLayoutsong = (LinearLayout) view.findViewById(R.id.songLayout);
        tb = view.findViewById(R.id.tab_layout);
        mHandler = new Handler();
        countsong=0;

        gname = view.findViewById(R.id.groupname);
      //  (getActivity()).tb.setVisibility(View.GONE);
        String groupname = DataHolderClass.getInstance().getDistributor_id();
        gname.setText(groupname);
         DatabaseReference ref = database.getReference("Groups/"+groupname+"/Songs");
         DatabaseReference ref2 = database.getReference("Groups/"+groupname+"/Users");
        if(ref!=null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String arrtemp1 = dataSnapshot.getValue().toString().substring(1, dataSnapshot.getValue().toString().length() - 1);
                    String arr[] = arrtemp1.toString().split(",");
                    for (String x : arr) {
                        //Toast.makeText(getActivity(),x.split("=")[1],Toast.LENGTH_LONG).show();
                        songList.add(x.split("=")[1]);
                        //Toast.makeText(getActivity(),songList.toString(),Toast.LENGTH_LONG).show();
                    }
                     count = mLinearLayoutsong.getChildCount();
                     //  Toast.makeText(getActivity(), "Count song", Toast.LENGTH_LONG).show();
                      //Toast.makeText(getActivity(), Integer.toString(count), Toast.LENGTH_LONG).show();
                    if (count == 1) {
                      //   Toast.makeText(getActivity(), "Inside Count Song", Toast.LENGTH_LONG).show();
                        int n = 0;

                        while (n != songList.size()) {
                            addSongCardView(songList.get(n));
                            n++;
                        }

                    }
                }



                //   Toast.makeText(getActivity(), "Toast", Toast.LENGTH_LONG).show();
                //  Toast.makeText(getActivity(), Integer.toString(count), Toast.LENGTH_LONG).show();


                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }

        if(ref2!=null) {
            ref2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                   // Toast.makeText(getActivity(), dataSnapshot.getValue().toString(), Toast.LENGTH_LONG).show();
                    String arrtemp1 = dataSnapshot.getValue().toString().substring(1, dataSnapshot.getValue().toString().length() - 1);
                    String arr[] = arrtemp1.toString().split(",");
                    for (String x : arr) {
                        memberList.add(x.split("=")[1]);
                      //  Toast.makeText(getActivity(), memberList.toString(), Toast.LENGTH_LONG).show();
                    }
                     int count1 = mLinearLayoutmember.getChildCount();
                      //Toast.makeText(getActivity(), "Count member", Toast.LENGTH_LONG).show();
                     //Toast.makeText(getActivity(), Integer.toString(count1), Toast.LENGTH_LONG).show();
                    View v = null;
                    if (count1 == 2) {
                       // Toast.makeText(getActivity(), "Inside Count member", Toast.LENGTH_LONG).show();
                        int n1 = 0;
                        while (n1 != memberList.size()) {
                            addMemberCardView(memberList.get(n1));
                            n1++;
                        }

                    }
                }

                //   Toast.makeText(getActivity(), "Toast", Toast.LENGTH_LONG).show();
                //  Toast.makeText(getActivity(), Integer.toString(count), Toast.LENGTH_LONG).show();


                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public void addMemberCardView(final String membername) {

      //  Toast.makeText(getActivity(),membername,Toast.LENGTH_LONG).show();
        //do something with your child element
        CardView card = new CardView(getActivity());

        // Set the CardView layoutParams
        card.setLayoutParams(new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.MATCH_PARENT
        ));
        //card.setId(groupid);
        card.setTag(membername);
        CardView.LayoutParams layoutParams = (CardView.LayoutParams) card.getLayoutParams();
        layoutParams.setMargins(0, 7, 0, 0);
        layoutParams.height = 250;
        //layoutParams.width = 120;
        card.setCardBackgroundColor(Color.parseColor("#9DE1FE"));
        card.setRadius(20);

        // Set cardView content padding
        card.setContentPadding(15, 15, 15, 15);
        // Set CardView elevation
        card.setCardElevation(2);
        //String tv = "tv" +n;
        // Initialize a new TextView to put in CardView
        TextView tv = new TextView(getActivity());
        tv.setLayoutParams(new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        ));
        tv.setHeight(80);
        final CardView.MarginLayoutParams lpt = (CardView.MarginLayoutParams) tv.getLayoutParams();
        lpt.setMargins(0, 2, 0, 0);
        tv.setText(membername);
        tv.setTextSize(18);
        tv.setTextColor(Color.BLACK);
        tv.setBackgroundColor(Color.parseColor("#FFFFFF"));

        // Put the TextView in CardView
        card.addView(tv);

        // Finally, add the CardView in root layout
        mLinearLayoutmember.addView(card);

    }
    public void addSongCardView(final String songname) {

     //   Toast.makeText(getActivity(),songname,Toast.LENGTH_LONG).show();
        //do something with your child element
        CardView card = new CardView(getActivity());

        // Set the CardView layoutParams
        card.setLayoutParams(new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.MATCH_PARENT
        ));
        //card.setId(groupid);
        card.setTag(songname);
        CardView.LayoutParams layoutParams = (CardView.LayoutParams) card.getLayoutParams();
        layoutParams.setMargins(0, 7, 0, 0);
        layoutParams.height = 250;
        //layoutParams.width = 120;
        card.setCardBackgroundColor(Color.parseColor("#9DE1FE"));
        card.setRadius(20);

        // Set cardView content padding
        card.setContentPadding(15, 15, 15, 15);
        // Set CardView elevation
        card.setCardElevation(2);
        //String tv = "tv" +n;
        // Initialize a new TextView to put in CardView
        TextView tv = new TextView(getActivity());
        tv.setLayoutParams(new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        ));
        tv.setHeight(80);
        final CardView.MarginLayoutParams lpt = (CardView.MarginLayoutParams) tv.getLayoutParams();
        lpt.setMargins(0, 2, 0, 0);
        tv.setText(songname);
        tv.setTextSize(18);
        tv.setBackgroundColor(Color.parseColor("#FFFFFF"));
       // tv.setBackgroundColor(Color.BLUE);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countsong%2!=0) {
                    stopPlaying();
                } else {
                    //Toast.makeText(getActivity(),"playing",Toast.LENGTH_LONG).show();
                    fetchAudioUrlFromFirebase(songname);

                }
                countsong++;
            }
        });
        // Put the TextView in CardView
        card.addView(tv);

        // Finally, add the CardView in root layout
        mLinearLayoutsong.addView(card);


    }

    public void fetchAudioUrlFromFirebase(String songname) {
        // songtext.setText(songname);
         Toast.makeText(getActivity(),"Playing " + songname,Toast.LENGTH_LONG).show();
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        final String songval = songname;
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://musicshare-bf3d6.appspot.com/"+songname+".mp3");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri) {
                try {
                    // Download url of file
                   //  Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                    final String url = uri.toString();
                    mMediaplayer = new MediaPlayer();
                    mMediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaplayer.setDataSource(url);
                    // wait for media player to get prepare
                    mMediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    } );
                    mMediaplayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("TAG", e.getMessage());
                    }
                });

    }
    public void stopPlaying(){
        if(mMediaplayer!=null){
            mMediaplayer.stop();
            mMediaplayer.release();
            mMediaplayer=null;
            Toast.makeText(getActivity(),"Stopped",Toast.LENGTH_SHORT).show();

        }
    }
}