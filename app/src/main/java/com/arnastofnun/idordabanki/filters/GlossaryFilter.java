package com.arnastofnun.idordabanki.filters;

import com.arnastofnun.idordabanki.models.Result;
import com.google.common.base.Predicate;
import java.util.regex.Pattern;

/**
 * A class that enables filtering of the target language
 * of a Result object
 * @author karlasgeir
 * @since 3/19/15.
 */
public final class GlossaryFilter implements Predicate<Result> {
    private final Pattern pattern; //The pattern

    /**
     * A function that creates a pattern by regex
     * @param regex - the regex
     */
    public GlossaryFilter(final String regex){
        pattern = Pattern.compile(regex);
    }

    /**
     * A function that applies the filter, returning
     * true or false depending on if the pattern fits
     * @param input the Result object to check for pattern
     * @return true/false depending on if the pattern fits
     */
    @Override
    public boolean apply(final Result input){
        return pattern.matcher(input.getDictionary_code()).find();
    }


}
