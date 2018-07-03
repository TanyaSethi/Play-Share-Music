package androidapp.musicshare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import android.support.annotation.NonNull;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class FirstFragment extends Fragment implements View.OnClickListener,SeekBar.OnSeekBarChangeListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;
    String userId;
    int count1=0;
    TextView songtext;
    String username;
    ArrayList<String> selectedGroups = new ArrayList<>();
    private DatabaseReference dbreference;
    ListView mList;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<String> groupList = new ArrayList<String>();
    SeekBar seekbr;
    Button playbutton1,playbutton2,playbutton3,playbutton4,playbutton5,playbutton6,playbutton7,playbutton8,playbutton9,playbutton10,share1,share2,share3,share4,share5,share6,share7,share8,share9,share10;
    Button stop1,stop2,stop3,stop4,stop5,stop6,stop7,stop8,stop9,stop10;
    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;
    private MediaPlayer mMediaplayer;
    Handler mHandler;
    TextView mDuration;
    private Runnable mRunnable;
    public FirstFragment() {
        // Required empty public constructor
    }
   /* DataPassListener mCallback;

    public interface DataPassListener{
        public void passData(String data);
    }*/

    public static FirstFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_blank, container, false);
//        TextView textView = (TextView) view;
//        textView.setText("Fragment #" + mPageNo);
        mHandler = new Handler();
        mDuration = (TextView)view.findViewById(R.id.duration);
        t1 = (TextView)view.findViewById(R.id.text1);
        t2 = (TextView)view.findViewById(R.id.text2);
        t3 = (TextView)view.findViewById(R.id.text3);
        t4 = (TextView)view.findViewById(R.id.text4);
        t5 = (TextView)view.findViewById(R.id.text5);
        t6 = (TextView)view.findViewById(R.id.text6);
        t7 = (TextView)view.findViewById(R.id.text7);
        t8 = (TextView)view.findViewById(R.id.text8);
        t9 = (TextView)view.findViewById(R.id.text9);
        t10 = (TextView)view.findViewById(R.id.text10);
        songtext = (TextView) view.findViewById(R.id.songname);
        t1.setText("You're Beautiful");
       // Toast.makeText(getActivity(),t1.getText(),Toast.LENGTH_LONG).show();
        t2.setText("Yeah!");
        t3.setText("Viva La Vida");
        t4.setText("TiK ToK (Untold Remix)");
        t5.setText("I Gotta Feeling");
        t6.setText("Girlfriend");
        t7.setText("Hey There Delilah");
        t8.setText("Don't Stop The Music");
        t9.setText("Bleeding Love");
        t10.setText("Apologize");
        seekbr = (SeekBar)view.findViewById(R.id.seek);
        mMediaplayer = new MediaPlayer();
        mMediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        playbutton1 = (Button) view.findViewById(R.id.playbutton1);
        playbutton2 = (Button) view.findViewById(R.id.playbutton2);
        playbutton3 = (Button) view.findViewById(R.id.playbutton3);
        playbutton4 = (Button) view.findViewById(R.id.playbutton4);
        playbutton5 = (Button) view.findViewById(R.id.playbutton5);
        playbutton6 = (Button) view.findViewById(R.id.playbutton6);
        playbutton7 = (Button) view.findViewById(R.id.playbutton7);
        playbutton8 = (Button) view.findViewById(R.id.playbutton8);
        playbutton9 = (Button) view.findViewById(R.id.playbutton9);
        playbutton10 = (Button) view.findViewById(R.id.playbutton10);
        share1 =(Button) view.findViewById(R.id.share1);
        share2 =(Button) view.findViewById(R.id.share2);
        share3 =(Button) view.findViewById(R.id.share3);
        share4 =(Button) view.findViewById(R.id.share4);
        share5 =(Button) view.findViewById(R.id.share5);
        share6 =(Button) view.findViewById(R.id.share6);
        share7 =(Button) view.findViewById(R.id.share7);
        share8 =(Button) view.findViewById(R.id.share8);
        share9 =(Button) view.findViewById(R.id.share9);
        share10 =(Button) view.findViewById(R.id.share10);
        stop1 =(Button) view.findViewById(R.id.stop1);
        stop2 =(Button) view.findViewById(R.id.stop2);
        stop3 =(Button) view.findViewById(R.id.stop3);
        stop4 =(Button) view.findViewById(R.id.stop4);
        stop5 =(Button) view.findViewById(R.id.stop5);
        stop6 =(Button) view.findViewById(R.id.stop6);
        stop7 =(Button) view.findViewById(R.id.stop7);
        stop8 =(Button) view.findViewById(R.id.stop8);
        stop9 =(Button) view.findViewById(R.id.stop9);
        stop10 =(Button) view.findViewById(R.id.stop10);

        //  Toast.makeText(getActivity(),"hi",Toast.LENGTH_LONG).show();
        playbutton1.setOnClickListener(this);
        playbutton2.setOnClickListener(this);
        playbutton3.setOnClickListener(this);
        playbutton4.setOnClickListener(this);
        playbutton5.setOnClickListener(this);
        playbutton6.setOnClickListener(this);
        playbutton7.setOnClickListener(this);
        playbutton8.setOnClickListener(this);
        playbutton9.setOnClickListener(this);
        playbutton10.setOnClickListener(this);
        share1.setOnClickListener(this);
        share2.setOnClickListener(this);
        share3.setOnClickListener(this);
        share4.setOnClickListener(this);
        share5.setOnClickListener(this);
        share6.setOnClickListener(this);
        share7.setOnClickListener(this);
        share8.setOnClickListener(this);
        share9.setOnClickListener(this);
        share10.setOnClickListener(this);
        stop1.setOnClickListener(this);
        stop2.setOnClickListener(this);
        stop3.setOnClickListener(this);
        stop4.setOnClickListener(this);
        stop5.setOnClickListener(this);
        stop6.setOnClickListener(this);
        stop7.setOnClickListener(this);
        stop8.setOnClickListener(this);
        stop9.setOnClickListener(this);
        stop10.setOnClickListener(this);
        seekbr.setOnSeekBarChangeListener(this) ;
        //Toast.makeText(getActivity(),userId,Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(),username,Toast.LENGTH_LONG).show();
        dbreference = FirebaseDatabase.getInstance().getReference();
        Home activity2 = (Home) getActivity();
        userId = activity2.getUserId();
        username = activity2.getUsername();
        DatabaseReference ref = database.getReference("User/" + username + "/Groups");
        if(ref!=null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Toast.makeText(getActivity(), dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
                     groupList.clear();
                    String arrtemp1 = dataSnapshot.getValue().toString().substring(1, dataSnapshot.getValue().toString().length() - 1);
                    String arr[] = arrtemp1.toString().split(",");
                    for (String x : arr) {
                        //Toast.makeText(getActivity(),x.split("=")[1],Toast.LENGTH_LONG).show();
                        groupList.add(x.split("=")[1]);
                    }
                }

                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }


        return view;
    }
    public void addRecentActivity(String add){
        dbreference.child("User").child(username).child("Recent Activity").push().setValue(add);
    }
    public void share(final String songname){
       LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.popuplist, null);
        selectedGroups.clear();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        addRecentActivity("You Shared " + songname);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        mList = promptsView.findViewById(R.id.listExample);
       mList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_multiple_choice,groupList);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedGroups.clear();
                SparseBooleanArray pos = mList.getCheckedItemPositions();
                for(int k=0;k<pos.size();k++){
                    /*if(pos.get(k))
                        Toast.makeText(getActivity(),"Position" + Integer.toString(k),Toast.LENGTH_LONG).show();*/
                }
                for(int k=0;k<pos.size();k++){
                    if(pos.get(k)){

                        selectedGroups.add(mList.getItemAtPosition(k).toString());
                    }
                }
             //   Toast.makeText(getActivity(),"Selected groups" + selectedGroups.toString(),Toast.LENGTH_LONG).show();
            }
        });
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Share",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                           //     Toast.makeText(getActivity(),"size" + Integer.toString(selectedGroups.size()),Toast.LENGTH_LONG).show();
                                for(int i=0;i<selectedGroups.size();i++) {
                               //     Toast.makeText(getActivity(),Integer.toString(i),Toast.LENGTH_LONG).show();
                                    dbreference.child("Groups").child(selectedGroups.get(i)).child("Songs").push().setValue(songname);
                                }
                                Toast.makeText(getActivity(),"Successfully Shared",Toast.LENGTH_LONG).show();
                             //   Toast.makeText(getActivity(),selectedGroups.get(0),Toast.LENGTH_LONG).show();

                                //mLinearLayout.postInvalidate();
                                //  Fragment currentFragment = getActivity().getFragmentManager().findFragmentById(R.id.fragment_second2);
                                //FragmentTransaction ft = getFragmentManager().beginTransaction();
                                //ft.detach().attach(this).commit();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
    public void onClick(View v) {
        // Toast.makeText(getActivity(),"hello",Toast.LENGTH_LONG).show();
        if ((v.getId() == R.id.share1) || (v.getId() == R.id.share2) || (v.getId() == R.id.share3) || (v.getId() == R.id.share4) || (v.getId() == R.id.share5) || (v.getId() == R.id.share6) || (v.getId() == R.id.share7) || (v.getId() == R.id.share8) || (v.getId() == R.id.share9) || (v.getId() == R.id.share10)) {
            int sharebuttonid = v.getId();
            String sharebuttoname = getResources().getResourceEntryName(sharebuttonid).toString();
            int number = Character.getNumericValue(sharebuttoname.charAt(sharebuttoname.length() - 1));
            String textViewname = "text" + Integer.toString(number);
            int resID = getResources().getIdentifier(textViewname, "id", getActivity().getPackageName());
            TextView t = (TextView) getActivity().findViewById(resID);
            getAudioStats();
            initializeSeekBar();
            String sharesongname = t.getText().toString();

            Toast.makeText(getActivity(),"Shared song" + sharesongname,Toast.LENGTH_LONG).show();
            share(sharesongname);
        }
        else if( ((v.getId() == R.id.stop1) || (v.getId() == R.id.stop2) || (v.getId() == R.id.stop3) || (v.getId() == R.id.stop4) || (v.getId() == R.id.stop5) || (v.getId() == R.id.stop6) || (v.getId() == R.id.stop7) || (v.getId() == R.id.stop8) || (v.getId() == R.id.stop9) || (v.getId() == R.id.stop10)))
        {
            stopPlaying();
        }
        else
         {
             stopPlaying();
            int playbuttonid = v.getId();
            // MyClass.MyMethod(ButtonName);
            String playbuttoname = getResources().getResourceEntryName(playbuttonid).toString();
            int number = Character.getNumericValue(playbuttoname.charAt(playbuttoname.length() - 1));
            String textViewname = "text" + Integer.toString(number);
            int resID = getResources().getIdentifier(textViewname, "id", getActivity().getPackageName());
            TextView t = (TextView) getActivity().findViewById(resID);
            String songname = t.getText().toString();
            Toast.makeText(getActivity(),"Playing "+songname,Toast.LENGTH_LONG).show();
           // getAudioStats();
            fetchAudioUrlFromFirebase(songname);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);

    }

    public void fetchAudioUrlFromFirebase(String songname) {
            songtext.setText(songname);
       // Toast.makeText(getActivity(),songname,Toast.LENGTH_LONG).show();
        addRecentActivity("You Played "+songname);
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        final String songval = songname;
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://musicshare-bf3d6.appspot.com/"+songname+".mp3");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri) {
                try {
                    // Download url of file
                   // Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
                    final String url = uri.toString();
                    mMediaplayer = new MediaPlayer();
                    mMediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaplayer.setDataSource(url);
                    // wait for media player to get prepare
                    mMediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                        public void onPrepared(MediaPlayer mp) {
                           /* if(mp.isPlaying()) {
                                stopPlaying();
                            }
                            mp = MediaPlayer.create(getActivity(),uri);*/
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
                        // Toast.makeText(MainActivity.this,"Failure",Toast.LENGTH_SHORT).show();
                        Log.i("TAG", e.getMessage());
                    }
                });

    }



    public View get_action(View view){
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    public void stopPlaying(){
        if(mMediaplayer!=null){
            mMediaplayer.stop();
            mMediaplayer.release();
            mMediaplayer=null;
            //Toast.makeText(getActivity(),"Stopped",Toast.LENGTH_SHORT).show();
            if(mHandler!=null)
                mHandler.removeCallbacks(mRunnable);
        }
    }
    protected void getAudioStats(){
        int duration  = mMediaplayer.getDuration()/1000; // In milliseconds
        int due = (mMediaplayer.getDuration() - mMediaplayer.getCurrentPosition())/1000;
        seekbr.setProgress(due);
        int pass = duration - due;
      //  mDuration.setText("" + duration + " seconds");
    }
    protected void initializeSeekBar(){
        seekbr.setMax(mMediaplayer.getDuration()/1000);

        mRunnable = new Runnable() {
            @Override
            public void run() {
              //  Toast.makeText(getActivity(),"Inside Run",Toast.LENGTH_LONG).show();
                if(mMediaplayer!=null){
                    int mCurrentPosition = mMediaplayer.getCurrentPosition()/1000; // In milliseconds
                   // Toast.makeText(getActivity(),mCurrentPosition,Toast.LENGTH_LONG).show();
                    seekbr.setProgress(mCurrentPosition);
                    getAudioStats();

                }
                mHandler.postDelayed(mRunnable,1000);
            }
        };
        mHandler.postDelayed(mRunnable,1000);
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(mMediaplayer!=null && b){

            mMediaplayer.seekTo(i*1000);
        }
    }


    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

