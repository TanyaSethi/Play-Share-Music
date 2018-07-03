package androidapp.musicshare;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.ListAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import Model.PojoClass;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final String ARG_PARAM2 = "param2";
    TextView sn ;
    public ImageView Image;
    protected RecyclerView rv;
    SeekBar seek;
    final static String DATA_RECEIVE = "data_receive";
    private ArrayList<PojoClass> pojoClassArrayList;
    private String[] song_name = {"Clean Bendit", "Look What You Made Me Do", "Boda Yello", "Unforgettable", "Strip That Down", "What Lovers Do"};
    private String[] singer_name = {"Symponi ft. Zavra", "Taylor Swift", "Cardi B", "French Montana", "Liam Payne, Quavo", "Maroon 5, sza"};
    private String[] time = {"2:22", "4:21", "2:58", "2:28", "5:39", "3:12"};
    protected ListAdapter listAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Context thiscontext;
    public static final String ARG_PAGE = "ARG_PAGE";
   // private OnFragmentInteractionListener mListener;
    boolean isselected = false;
    public ThirdFragment() {
        // Required empty public constructor
    }

    public static ThirdFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        ThirdFragment fragment = new ThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThirdFragment newInstance(String param1, String param2) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void onAttach(Context context) {

        super.onAttach(context);

       // thiscontext = context;
    }
   /* public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getActivity());

        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setLayoutManager(mLayoutManager);
        listAdapter = new ListAdapter(getActivity(), pojoClassArrayList);
        rv.setAdapter(listAdapter);
    }*/
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
       // Inflate the layout for this fragment

       final View rootView = inflater.inflate(R.layout.activity_screen1, container, false);
       Image = (ImageView) rootView.findViewById(R.id.playbutton1);
       sn = (TextView) rootView.findViewById(R.id.song_name);
       LayoutInflater li = LayoutInflater.from(getActivity());
       View view = li.inflate(R.layout.activity_home2, null);


      /* Bundle args = getArguments();
       if (args != null && args.containsKey("songname")) {
           String songname = args.getString("songname");
           Toast.makeText(getActivity(), songname, Toast.LENGTH_LONG).show();
           sn.setText(songname);

       }*/
           Image.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   if (isselected == false) {
                       Image.setImageResource(R.drawable.ic_pause);
                       isselected = true;
                   } else {
                       Image.setImageResource(R.drawable.ic_play);
                       isselected = false;
                   }

               }
           });
         rv = rootView.findViewById(R.id.rv);
         seek = (SeekBar)rootView.findViewById(R.id.seek);
           mLayoutManager = new LinearLayoutManager(getActivity());

           rv = (RecyclerView) rootView.findViewById(R.id.rv);
           rv.setItemAnimator(new DefaultItemAnimator());
           rv.setLayoutManager(mLayoutManager);
           listAdapter = new ListAdapter(getActivity(), pojoClassArrayList);
           rv.setAdapter(listAdapter);
           return rootView;
       }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        pojoClassArrayList = new ArrayList<>();

        for (int i = 0; i < song_name.length; i++) {

            PojoClass pojoClass = new PojoClass();
            pojoClass.setSong_name(song_name[i]);
            pojoClass.setSinger_name(singer_name[i]);
            pojoClass.setTime(time[i]);

            pojoClassArrayList.add(pojoClass);
        }

   /*     final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
       /* recyclerView.setLayoutManager(layoutManager);*//*
        listAdapter = new ListAdapter(getActivity(), pojoClassArrayList);
       RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(listAdapter);*/


    }



    public void onDetach() {
        super.onDetach();

    }


   /* public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            songname.setText(args.getString(DATA_RECEIVE));
        }
    }*/

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

}

