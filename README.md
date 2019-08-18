# AndroidSearchView (androidX)
android SearchView (androidx.appcompat.widget.SearchView) widget is very helpful and awesome widget. 
Please use its very cool  and reduce boiler code instead of few steps.


### Review : 
[![See](https://img.youtube.com/vi/Io65s9TJ6Mo/0.jpg)](https://www.youtube.com/watch?v=Io65s9TJ6Mo)



### Code structure : 

![alt text](https://github.com/datanapps/AndroidSearchView/blob/master/screens/code_structure.PNG)


### Download APK : 

https://github.com/datanapps/AndroidSearchView/blob/master/screens/app-debug.apk

##### Searching with MainAcrivity ----> SearchResultActivity



#### 1. Create option menu : R.menu.menu.xml :

        <?xml version="1.0" encoding="utf-8"?>
        <menu xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto">
            <item android:id="@+id/search"
                android:title="@string/app_name"
                android:icon="@android:drawable/ic_menu_search"
                app:showAsAction="always"
                app:actionViewClass="androidx.appcompat.widget.SearchView" />
        </menu>

#### 2. MainActivity.class : 

          public class MainActivity extends AppCompatActivity {

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

              //set toolbar
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

            }


            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu, menu);

                // Associate searchable configuration with the SearchView
                SearchManager searchManager =
                        (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                SearchView searchView =
                        (SearchView) menu.findItem(R.id.search).getActionView();
                searchView.setMaxWidth(Integer.MAX_VALUE);
                searchView.setSearchableInfo(
                        searchManager.getSearchableInfo(getComponentName()));

                return true;
            }
        }



#### 3. Create searchable.xml

        <?xml version="1.0" encoding="utf-8"?>
        <searchable xmlns:android="http://schemas.android.com/apk/res/android"
            android:label="@string/app_name"
            android:hint="search"
            android:voiceSearchMode="showVoiceSearchButton|launchRecognizer"
            android:searchSuggestAuthority="com.datanapps.androidsearchview.MySuggestionProvider"
            android:searchSuggestIntentAction="android.intent.action.SEARCH"
            android:searchSuggestSelection=""
            >
        </searchable>
        
#### 4. Handle Intent in SearchResultActivity.class 

        public class SearchResultsActivity extends AppCompatActivity {

            SearchView searchView;

            private TextView textView;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_searchable);

                //settoolbar
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                textView = findViewById(R.id.textview);

               // manage search view
                manageSearchView();

                // handle new imtent
                handleIntent(getIntent());
            }


            private void manageSearchView() {
                SearchManager searchManager =
                        (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                searchView =
                        findViewById(R.id.searchView);
                searchView.setSearchableInfo(
                        searchManager.getSearchableInfo(getComponentName()));


                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        textView.setText(newText);
                        return false;
                    }
                });

                searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
                    @Override
                    public boolean onSuggestionSelect(int position) {
                        return false;
                    }

                    @Override
                    public boolean onSuggestionClick(int position) {
                        return false;
                    }
                });
            }


            private void saveCurrentData(String query) {
                if (query != null) {
                    SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                            MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
                    suggestions.saveRecentQuery(query, null);
                }
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case android.R.id.home:
                        finish();
                        return true;
                    default:
                        return super.onOptionsItemSelected(item);
                }
            }

            @Override
            protected void onNewIntent(Intent intent) {
                setIntent(intent);
                handleIntent(intent);
            }

            private void handleIntent(Intent intent) {
                if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                    final String query = intent.getStringExtra(SearchManager.QUERY);
                    saveCurrentData(query);

                    searchView.post(new Runnable() {

                        @Override
                        public void run() {
                            // Important! Make sure searchView has been initialized
                            // and referenced to the correct (current) SearchView.
                            // Config changes (e.g. screen rotation) may make the
                            // variable value null.
                            searchView.setQuery(query, false);
                            textView.setText(query);
                        }
                    });



                }
            }

        }
        
#### 5. Now add carefully all tag in manifest file :

            <?xml version="1.0" encoding="utf-8"?>
            <manifest xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:tools="http://schemas.android.com/tools"
                package="com.datanapps.androidsearchview">

                <application
                    android:allowBackup="true"
                    android:icon="@mipmap/ic_launcher"
                    android:label="@string/app_name"
                    android:roundIcon="@mipmap/ic_launcher_round"
                    android:supportsRtl="true"
                    android:theme="@style/AppTheme.NoActionBar"
                    tools:ignore="GoogleAppIndexingWarning">

                    <activity
                        android:name="com.datanapps.androidsearchview.MainActivity"
                        android:launchMode="singleTop">
                        <meta-data
                            android:name="android.app.default_searchable"
                            android:value="com.datanapps.androidsearchview.SearchResultsActivity"/>

                        <intent-filter>
                            <action android:name="android.intent.action.MAIN" />
                            <category android:name="android.intent.category.LAUNCHER" />
                        </intent-filter>

                    </activity>

                    <activity
                        android:name="com.datanapps.androidsearchview.SearchResultsActivity"
                        android:launchMode="singleTop"
                        android:parentActivityName=".MainActivity">
                        <meta-data
                            android:name="android.support.PARENT_ACTIVITY"
                            android:value="com.datanapps.androidsearchview.MainActivity" />

                        <meta-data
                            android:name="android.app.searchable"
                            android:resource="@xml/searchable" />

                        <intent-filter>
                            <action android:name="android.intent.action.SEARCH" />
                        </intent-filter>
                    </activity>





                    <provider
                        android:name=".MySuggestionProvider"
                        android:authorities="com.datanapps.androidsearchview.MySuggestionProvider" />




                </application>

            </manifest>

