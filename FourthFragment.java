package androidapp.musicshare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link FourthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FourthFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference dbreference;
    LinearLayout mLinearLayout;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
   // ExpandableHeightGridView gridview;
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;
    TextView name;
    //private int[] Image = {R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5,R.drawable.image6};
    private ArrayList<Beanclass> beans;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String username;
    ArrayList<String> songList = new ArrayList<>();
    ///private GridviewAdapter gridviewAdapter;

    public FourthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FourthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FourthFragment newInstance(String param1, String param2) {
        FourthFragment fragment = new FourthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_fourth, container, false);
        Context context;
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.pro2);
        name = rootview.findViewById(R.id.name);
        Home activity = (Home) getActivity();
        username = activity.getUsername();
        name.setText("Hello " + username);
        mLinearLayout = (LinearLayout) rootview.findViewById(R.id.parentlineralayout);
     /*   gridview = (ExpandableHeightGridView) rootview.findViewById(R.id.gridview);
        beans = new ArrayList<Beanclass>();
*/
       /* for (int i = 0; i < songList.size(); i++) {

            Beanclass beanclass = new Beanclass(songList.get(i).toString());
            beans.add(beanclass);

        }
       // gridviewAdapter = new GridviewAdapter(getActivity(), beans);
        //gridview.setExpanded(true);

        gridview.setAdapter(gridviewAdapter);
*/
        songList.clear();
        getRecentActivity();
        return rootview;
    }
    public void getRecentActivity() {
        DatabaseReference ref = database.getReference("User/" + username + "/Recent Activity");
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Toast.makeText(getActivity(), dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
                    // groupList.clear();
                    String arrtemp1 = dataSnapshot.getValue().toString().substring(1, dataSnapshot.getValue().toString().length() - 1);
                    String arr[] = arrtemp1.toString().split(",");
                    for (String x : arr) {
                        //Toast.makeText(getActivity(),x.split("=")[1],Toast.LENGTH_LONG).show();
                        songList.add(x.split("=")[1]);
                    }
                    int count = mLinearLayout.getChildCount();
                   //   Toast.makeText(getActivity(), "Count", Toast.LENGTH_LONG).show();
                //      Toast.makeText(getActivity(), Integer.toString(count), Toast.LENGTH_LONG).show();
                    View v = null;
                    if (count == 2) {
                      //   Toast.makeText(getActivity(), "Inside Count", Toast.LENGTH_LONG).show();
                        int n = 0;

                        while (n != songList.size()) {
                            addCardView(songList.get(n));
                            n++;
                        }
                    }
                }

                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }

    }

    public static FourthFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        FourthFragment fragment = new FourthFragment();
        fragment.setArguments(args);
        return fragment;
    }
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);
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

    public void addCardView(String Activityname){
        CardView card = new CardView(getActivity());

        // Set the CardView layoutParams
        card.setLayoutParams(new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.MATCH_PARENT
        ));
        //card.setId(groupid);
        card.setTag(Activityname);
        CardView.LayoutParams layoutParams = (CardView.LayoutParams) card.getLayoutParams();
        layoutParams.setMargins(0, 7, 0, 0);
        layoutParams.height = 250;
        //layoutParams.width = 120;
        card.setCardBackgroundColor(Color.parseColor("#9DE1FE"));
        card.setRadius(20);


        // card.setLayoutParams(params);

        // Set CardView corner radius
        card.setRadius(2);

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
        tv.setText(Activityname);
        tv.setTextSize(18);
        tv.setTextColor(Color.BLACK);
        tv.setBackgroundColor(Color.parseColor("#FFFFFF"));
        // Put the TextView in CardView
        card.addView(tv);

        // Finally, add the CardView in root layout
        mLinearLayout.addView(card);

    }

}
