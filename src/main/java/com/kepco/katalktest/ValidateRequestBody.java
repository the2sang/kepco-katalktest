package com.kepco.katalktest;

import com.sun.media.sound.SoftAudioPusher;

import java.util.ArrayList;
import java.util.List;

public class ValidateRequestBody {

    private List<SaupParam> businesses;

    public ValidateRequestBody() {
        this.businesses = new ArrayList<SaupParam>();
    }

    public void addParam(SaupParam saupParam) {
        businesses.add(saupParam);
    }


}
