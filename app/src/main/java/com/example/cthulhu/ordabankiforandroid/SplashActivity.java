    /**
     * localisedLangs is a list of strings that are names of languages
     */
    public ArrayList<ArrayList<String>> localisedLangs;
    /**
     * localisedLangs is list of strings that are dictionary names
     */    
    public ArrayList<ArrayList<String>> localisedDicts;
    /**
     * localisedLangs is a list of glossaries
     */
    public ArrayList<Glossary> glossaries;
    /**
     * dObtained is true if dictionary values have been obtained
     */
    private boolean dObtained;
    /**
     * lObtained is true if language values have been obtained
     */
    private boolean lObtained;
    /**
     * error is true if an error appears while the app is running
     */
    private boolean error;
    /**
     * startTime is the current time im milliseconds
     */
    long startTime;
    /**
     * dJsonHandler is handles json files that contain dictionary values
     */
    DictionaryJsonHandler dJsonHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startTime = System.currentTimeMillis();
        isLocaleSet();
        dObtained = false;
        lObtained = false;
        error =false;

        getLocalisedLangs();

        getLocalisedDicts();

        checkTiming();
    }

    /**
     * Gets the system's locale settings and checks if locale has been set
     * @param nothing
     * @return nothing
     */
    private void isLocaleSet(){
        final LocaleSettings localeSettings = new LocaleSettings(this);
        //if no language set in locale go to select language
        if (!localeSettings.getLocaleStatus()) {
            Intent intent = new Intent(SplashActivity.this, SelectLanguageActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }

    }
    @Override
    public void onDictionariesObtained (Dictionary[]dictionaries){
        glossaries = new ArrayList<Glossary>();
        localisedDicts = new ArrayList<ArrayList<String>>();
        int index = 0;
        Glossary glossary;
        ArrayList<String> codeList = new ArrayList<String>();
        ArrayList<String> dictList = new ArrayList<String>();
        //Toast.makeText(getApplicationContext(), "dLoop", Toast.LENGTH_SHORT).show();
        for (Dictionary dict : dictionaries) {
            codeList.add(index, dict.getDictCode());
            dictList.add(index,dict.getDictName());
            glossary = new Glossary(dict.getDictCode(),dict.getDictName());
            glossaries.add(index, glossary);
            index++;
        }
        localisedDicts.add(0,codeList);
        localisedDicts.add(1, dictList);
        Collections.sort(glossaries);
        dObtained=true;
    }
    @Override
    public void onDictionariesFailure (int statusCode){
        error = true;
        Toast.makeText(getApplicationContext(), "dictionary error", Toast.LENGTH_SHORT).show();
        //todo handle failure: error message, restart quit options
    }
    @Override
    public void onLanguagesObtained (Language[]languages){
        localisedLangs = new ArrayList<ArrayList<String>>();
        int index = 2;
        ArrayList<String> codeList = new ArrayList<String>();
        codeList.add(0,null);
        codeList.add(1,null);
        ArrayList<String> nameList = new ArrayList<String>();
        nameList.add(0,null);
        nameList.add(1,null);
        List<Language> langarray = Arrays.asList(languages);
        Collections.sort(langarray);
        for (Language lang : langarray) {
            if(lang.getLangCode().equals("IS")){
                codeList.set(0,lang.getLangCode());
                nameList.set(0,lang.getLangName());
            }
            else if(lang.getLangCode().equals("EN")){
                codeList.set(1,lang.getLangCode());
                nameList.set(1,lang.getLangName());
            }
            else {
                codeList.add(index, lang.getLangCode());
                nameList.add(index, lang.getLangName());
                index++;
            }

        }
        localisedLangs.add(0,codeList);
        localisedLangs.add(1,nameList);

        lObtained=true;
    }
    @Override
    public void onLanguagesFailure (int statusCode){
        error= true;
        Toast.makeText(getApplicationContext(), "languages error", Toast.LENGTH_SHORT).show();
        //todo handle failure: error message, restart quit options
    }

    private void getLocalisedLangs(){
        //calls rest client to populate languages array
        final String langURL = "http://api.arnastofnun.is/ordabanki.php?list=langs&agent=ordabankaapp";
        LanguageJsonHandler lJsonHandler = new LanguageJsonHandler(this);
        OrdabankiRestClientUsage langClient = new OrdabankiRestClientUsage();
        try {
            //Toast.makeText(getApplicationContext(), "getting languages", Toast.LENGTH_SHORT).show();
            langClient.getLanguages(langURL, lJsonHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getLocalisedDicts(){
        //calls rest client to populate dictionaries array
        final String dictURL = "http://api.arnastofnun.is/ordabanki.php?list=dicts&agent=ordabankaapp";
        dJsonHandler = new DictionaryJsonHandler(this);
        OrdabankiRestClientUsage dictClient = new OrdabankiRestClientUsage();
        try {
            //Toast.makeText(getApplicationContext(), "getting dictionaries", Toast.LENGTH_SHORT).show();
            dictClient.getDictionaries(dictURL, dJsonHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void checkTiming(){
        //checks if splash screen has been up for a minimum of 2 seconds then moves to search screen
        final Globals globals = (Globals) getApplicationContext();
        Runnable runnable = new Runnable() {
            public void run() {

                Looper.prepare();
                long endTime = startTime + 2000;
                long delay = 0;
                while (!error) {
                    //Log.v("dObtained", String.valueOf(dObtained));
                    //Log.v("lObtained",String.valueOf(lObtained));
                    if (dObtained && lObtained) {
                        long now = System.currentTimeMillis();
                        if (now < endTime) {
                            delay = endTime - now;
                        }
                        break;
                    }
                }

                globals.setLanguages(localisedLangs);
                globals.setDictionaries(glossaries);
                globals.setLocalizedDictionaries(localisedDicts);
                Handler mainHandler = new Handler(SplashActivity.this.getMainLooper());
                mainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LocaleSettings localeSettings = new LocaleSettings(SplashActivity.this);
                        localeSettings.setLanguageFromPref(SearchScreen.class);
                    }
                }, delay);
            }
        };
        Thread timingThread = new Thread(runnable);
        timingThread.start();

    }


}

