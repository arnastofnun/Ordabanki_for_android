package com.example.cthulhu.ordabankiforandroid;

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

/**
 * Holder object for parsing synonym results
 * @author Bill
 * @since 04/11/14
  */
public class SynonymResult {
    String synonym;
    Word word;
 /**
  * Holder object for parsing word results
  * @author Bill
  * @since 04/11/14
  * todo add getters and setters
   */
    public static class Word{
        String term_id;
        String word;
        String dict_code;
    }
}
