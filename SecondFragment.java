package androidapp.musicshare;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SecondFragment extends Fragment implements View.OnClickListener {
    private DatabaseReference dbreference;
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;
    String userId;
    String username;
    Button createGroup;
    Button joinGroup;

    LinearLayout mLinearLayout;
    int i = 0;

    // ArrayList<String> users = new ArrayList<>();
    //ArrayList<String> groups = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<String> groupList = new ArrayList<String>();

    public SecondFragment() {
        // Required empty public constructor
    }

    public static SecondFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        SecondFragment fragment = new SecondFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View Rootview = inflater.inflate(R.layout.fragment_second2, container, false);
        /*Bundle b = getArguments();
        if(b!=null) {
            userId = b.getString("UserId");
            username = b.getString("Username");
        }*/
      //  FragmentTransaction.add(SecondFragment frag,"second");

        Home activity = (Home) getActivity();
        userId = activity.getUserId();
        username = activity.getUsername();
       // Toast.makeText(getActivity(), "Toasterrrr", Toast.LENGTH_LONG).show();

        mLinearLayout = (LinearLayout) Rootview.findViewById(R.id.cardviewroot);
        dbreference = FirebaseDatabase.getInstance().getReference();
        createGroup = (Button) Rootview.findViewById(R.id.create);
        joinGroup = (Button) Rootview.findViewById(R.id.join);
        createGroup.setOnClickListener(this);
        joinGroup.setOnClickListener(this);
        //tb = view.findViewById(R.id.tab_layout);
        DatabaseReference ref = database.getReference("User/" + username + "/Groups");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Toast.makeText(getActivity(), dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
                String arrtemp1 = dataSnapshot.getValue().toString().substring(1, dataSnapshot.getValue().toString().length() - 1);
                String arr[] = arrtemp1.toString().split(",");
                for (String x : arr) {
                    //Toast.makeText(getActivity(),x.split("=")[1],Toast.LENGTH_LONG).show();
                    groupList.add(x.split("=")[1]);
                }

                int count = mLinearLayout.getChildCount();
             //   Toast.makeText(getActivity(), "Toast", Toast.LENGTH_LONG).show();
              //  Toast.makeText(getActivity(), Integer.toString(count), Toast.LENGTH_LONG).show();
                View v = null;
                if (count == 0) {
                   // Toast.makeText(getActivity(), "Inside Count", Toast.LENGTH_LONG).show();
                    int n = 0;

                    while (n != groupList.size()) {
                        addCardView(groupList.get(n), n);
                        n++;
                    }
                } else {
                  /* for (int i = 0;  i < count; i++) {
                       v = mLinearLayout.getChildAt(i);*/
                      // Toast.makeText(getActivity(), v.getTag(i).toString(), Toast.LENGTH_LONG).show();
                      // Toast.makeText(getActivity(), groupList.get(n), Toast.LENGTH_LONG).show();
                      /* if( ! (v.findViewWithTag(groupList.get(i)).toString().equals(groupList.get(i)))){
                        Toast.makeText(getActivity(), "Inside if", Toast.LENGTH_LONG).show();*/
                       //addCardView(groupList.get(n),n);
               /*     mLinearLayout.removeAllViews();
                    mLinearLayout.forceLayout();
                    int n1=0;
                    while (n1 != groupList.size()) {
                        addCardView(groupList.get(n1), n1);
                        n1++;*/
                    //}
                //}
                   }

               // }
            }



                  /*  }
                    if(count!=0 && getResources().getResourceEntryName(v.getId()).toString()==Integer.toString(n)){
                        addCardView(groupList.get(n),n);
                    }
                }


            }*/


            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

          return Rootview;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.create) {
            // get prompts.xml view
            LayoutInflater li = LayoutInflater.from(getActivity());
            View promptsView = li.inflate(R.layout.prompts, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getActivity());

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    // edit text
                                    // Toast.makeText(getActivity(),userInput.getText().toString(),Toast.LENGTH_LONG).show();
                                    //Toast.makeText(getActivity(),userId.toString(),Toast.LENGTH_LONG).show();
                                    //  var ref = firebase.database().ref("users/ada");
                                    dbreference.child("User").child(username).child("Groups").push().setValue(userInput.getText().toString());
                                    dbreference.child("Groups").child(userInput.getText().toString()).child("Users").push().setValue(username);
                                    mLinearLayout.removeAllViews();
                                    //mLinearLayout.getView().
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
        if (v.getId() == R.id.join) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            View promptsView = li.inflate(R.layout.prompts, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getActivity());

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText userIn = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    // edit text
                                 //   Toast.makeText(getActivity(), userIn.getText().toString(), Toast.LENGTH_LONG).show();
                                    dbreference.child("User").child(username).child("Groups").push().setValue(userIn.getText().toString());
                                    dbreference.child("Groups").child(userIn.getText().toString()).child("Users").push().setValue(username);
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
    }


    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void addCardView(final String group,int groupid) {


                    //do something with your child element
                    CardView card = new CardView(getActivity());

                    // Set the CardView layoutParams
                    card.setLayoutParams(new CardView.LayoutParams(
                            CardView.LayoutParams.MATCH_PARENT,
                            CardView.LayoutParams.MATCH_PARENT
                    ));
                    card.setTag(group);
                    card.setMaxCardElevation(25);
                    card.setRadius(20);
                    card.setContentPadding(10,10,10,10);
                    CardView.LayoutParams layoutParams = (CardView.LayoutParams) card.getLayoutParams();
                    layoutParams.setMargins(0, 15, 0, 0);
                    layoutParams.height = 250;
                    card.setCardBackgroundColor(Color.parseColor("#9DE1FE"));
                    card.setRadius(20);

                    // Set cardView content padding
                    card.setContentPadding(15, 15, 15, 15);
                    // Set CardView elevation
                    card.setCardElevation(25);
                    //String tv = "tv" +n;
                    // Initialize a new TextView to put in CardView
                    TextView tv = new TextView(getActivity());
                    tv.setLayoutParams(new CardView.LayoutParams(
                            CardView.LayoutParams.MATCH_PARENT,
                            CardView.LayoutParams.WRAP_CONTENT
                    ));
                    tv.setHeight(100);
                    final CardView.MarginLayoutParams lpt = (CardView.MarginLayoutParams) tv.getLayoutParams();
                    lpt.setMargins(0, 50, 0, 0);
                    tv.setText(group);
                    tv.setTextSize(18);
                    tv.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    tv.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                          //  Toast.makeText(getActivity(), "Hello People", Toast.LENGTH_LONG).show();
                            DataHolderClass.getInstance().setDistributor_id(group);
                            Fragment fragment = new groupDetails();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container1, fragment);
                            fragmentTransaction.addToBackStack(null);
                            //tb.setVisibility(View.GONE);
                            fragmentTransaction.commit();
                            /*Intent ThirdFragment = new Intent();
                            startActivity(ThirdFragment);*/
                        }
                    });
                    // Put the TextView in CardView
                    card.addView(tv);

                    // Finally, add the CardView in root layout
                    mLinearLayout.addView(card);

                }

   /* public void onBackPressed() {
        {
            tb.setVisibility(View.VISIBLE);
        }
    }*/


    }

