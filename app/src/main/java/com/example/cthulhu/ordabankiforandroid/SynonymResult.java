package com.example.cthulhu.ordabankiforandroid;

/**
 * Created by cthulhu on 04/11/14.
 */
/*
*Example:
* [{"synonym":"svið","word":{"term_id": 454306, "word": "ríki", "dict_code": "STJORN"}},
* {"synonym":"svið","word":{"term_id": 450984, "word": "ríki", "dict_code": "STJORN"}},
* {"synonym":"svið","word":{"term_id": 450788, "word": "skrifstofa", "dict_code": "STJORN"}},
* {"synonym":"svið","word":{"term_id": 446745, "word": "umfang", "dict_code": "SJOMENN"}},
* {"synonym":"svið","word":{"term_id": 374992, "word": "yfirráðasvið", "dict_code": "LAND"}},
 * {"synonym":"svið","word":{"term_id": 523226, "word": "stig", "dict_code": "UPPL"}},
 * {"synonym":"svið","word":{"term_id": 720703, "word": "svæði", "dict_code": "LISA"}}]
*
 */
public class SynonymResult {
    String synonym;
    Word word;

    public static class Word{
        String term_id;
        String word;
        String dict_code;
    }
}
