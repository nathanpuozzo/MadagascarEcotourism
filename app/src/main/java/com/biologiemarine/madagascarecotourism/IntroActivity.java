package com.biologiemarine.madagascarecotourism;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Slide 1
        addSlide(AppIntroFragment.newInstance("Bienvenue sur l'application Madagascar Ecotourism","La première application concernant le tourisme responsable, soucieux de l’équité que l’écotourisme doit apporter à la population du pays",R.mipmap.logo,ContextCompat.getColor(getApplicationContext(),R.color.IntroBG)));

        //Slide 2
        addSlide(AppIntroFragment.newInstance("Hôtels","Planifiez votre voyage en ayant des informations traditionnelles sur les hôtels et des renseignements sur leurs performances écotouristiques",R.mipmap.localisation,ContextCompat.getColor(getApplicationContext(),R.color.IntroBG)));

        //Slide 3
        addSlide(AppIntroFragment.newInstance("Aires protégées","Découvrez les aires protégées marines et terrestres de Madagascar aux travers de cartes démonstratives et en ayant accès aux recherches concernant leur biodiversité",R.mipmap.decouvrir,ContextCompat.getColor(getApplicationContext(),R.color.IntroBG)));

        //Slide 4
        addSlide(AppIntroFragment.newInstance("Guides locaux","Communiquez directement avec les guides locaux pour organiser votre voyage à un prix équitable ",R.mipmap.experts,ContextCompat.getColor(getApplicationContext(),R.color.IntroBG)));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        finish();
    }

}
