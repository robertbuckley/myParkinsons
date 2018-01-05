package com.buckley.robert.parkinsonszero;

/**
 * Created by Robert on 4/18/2016.
 */
public class Form {
    boolean family;
    boolean male;
    boolean female;
    boolean other;
    boolean adhd;
    boolean add;
    boolean admed;
    boolean depressed;
    boolean factory;
    boolean mptp;
    boolean antipsychotics;
    boolean drugs;
    boolean nicotine;
    boolean narcolepsy;
    boolean famnarcolepsy;
    boolean skydive;
    int bones;
    int age;
    int since;
    String id;

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public boolean isFamily() {
        return family;
    }

    public boolean isMale() {
        return male;
    }

    public boolean isFemale() {
        return female;
    }

    public boolean isOther() {
        return other;
    }

    public boolean isAdhd() {
        return adhd;
    }

    public boolean isAdd() {
        return add;
    }

    public boolean isAdmed() {
        return admed;
    }

    public boolean isDepressed() {
        return depressed;
    }

    public boolean isFactory() {
        return factory;
    }

    public boolean isMptp() {
        return mptp;
    }

    public boolean isAntipsychotics() {
        return antipsychotics;
    }

    public boolean isDrugs() {
        return drugs;
    }

    public boolean isNicotine() {
        return nicotine;
    }

    public boolean isNarcolepsy() {
        return narcolepsy;
    }

    public boolean isFamnarcolepsy() {
        return famnarcolepsy;
    }

    public boolean isSkydive() {
        return skydive;
    }

    public int getBones() {
        return bones;
    }

    public int getAge() {
        return age;
    }

    public void setFamily(boolean family) {
        this.family = family;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public void setFemale(boolean female) {
        this.female = female;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public void setAdhd(boolean adhd) {
        this.adhd = adhd;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public void setAdmed(boolean admed) {
        this.admed = admed;
    }

    public void setDepressed(boolean depressed) {
        this.depressed = depressed;
    }

    public void setFactory(boolean factory) {
        this.factory = factory;
    }

    public void setMptp(boolean mptp) {
        this.mptp = mptp;
    }

    public void setAntipsychotics(boolean antipsychotics) {
        this.antipsychotics = antipsychotics;
    }

    public void setDrugs(boolean drugs) {
        this.drugs = drugs;
    }

    public void setNicotine(boolean nicotine) {
        this.nicotine = nicotine;
    }

    public void setNarcolepsy(boolean narcolepsy) {
        this.narcolepsy = narcolepsy;
    }

    public void setFamnarcolepsy(boolean famnarcolepsy) {
        this.famnarcolepsy = famnarcolepsy;
    }

    public void setSkydive(boolean skydive) {
        this.skydive = skydive;
    }

    public void setBones(int bones) {
        this.bones = bones;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSince(int since) {
        this.since = since;
    }

    public int getSince() {
        return since;
    }

    public Form(boolean family, boolean male, boolean female, boolean other, boolean adhd, boolean add, boolean admed, boolean depressed, boolean factory, boolean mptp, boolean antipsychotics, boolean drugs, boolean nicotine, boolean narcolepsy, boolean famnarcolepsy, boolean skydive, int bones, int age, int since, String id) {
        this.family = family;
        this.male = male;
        this.female = female;
        this.other = other;
        this.adhd = adhd;
        this.add = add;
        this.admed = admed;
        this.depressed = depressed;
        this.factory = factory;
        this.mptp = mptp;
        this.antipsychotics = antipsychotics;
        this.drugs = drugs;
        this.nicotine = nicotine;
        this.narcolepsy = narcolepsy;
        this.famnarcolepsy = famnarcolepsy;
        this.skydive = skydive;
        this.bones = bones;
        this.age = age;
        this.since = since;
        this.id = id;
    }
}
