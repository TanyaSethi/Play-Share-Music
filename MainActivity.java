package androidapp.musicshare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

//import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;

public class MainActivity extends AppCompatActivity {
    public FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateLitsener;
    public static final int RC_SIGN_IN = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateLitsener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null) {
                    Intent i =new Intent(MainActivity.this,Home.class);
                    i.putExtra("UserId",user.getUid());
                    i.putExtra("Username",user.getDisplayName());
                    startActivity(i);
                }
                else {

                    startActivityForResult(

                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()                                            ))
                                    .build(),
                            RC_SIGN_IN);
                }

            }
        };
    }
    public void onResume(){
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateLitsener);
    }
    public void onPause(){
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateLitsener);
    }
}
