package me.abbatrombone.traz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Stratagems {

    public static String[] stratagemChoice(){
        List<String> stratagemList = new ArrayList<>();
        Random random = new Random();
        String[] stratagemarray = {
                "TX-41 Sterilizer",
                "StA-X3 W.A.S.P. Launcher",
                "SH-51 Directional Shield",
                "SH-32 Shield Generator Pack",
                "SH-20 Ballistic Shield Backpack",
                "RS-422 Railgun",
                "RL-77 Airburst Rocket Launcher",
                "Orbital Walking Barrage",
                "Orbital Smoke Strike",
                "Orbital Railcannon Strike",
                "Orbital Precision Strike",
                "Orbital Napalm Barrage",
                "Orbital Laser",
                "Orbital Gatling Barrage",
                "Orbital Gas Strike",
                "Orbital EMS Strike",
                "Orbital Airburst Strike",
                "Orbital 380mm HE Barrage",
                "Orbital 120mm HE Barrage",
                "MLS-4X Commando",
                "MG-206 Heavy Machine Gun",
                "MG-43 Machine Gun",
                "MD-I4 Incendiary Mines",
                "MD-17 Anti-Tank Mines",
                "MD-8 Gas Mines",
                "MD-6 Anti-Personnel Minefield",
                "M-105 Stalwart",
                "M-102 Fast Recon Vehicle",
                "LIFT-850 Jump Pack",
                "LAS-99 Quasar Cannon",
                "LAS-98 Laser Cannon",
                "GR-8 Recoilless Rifle",
                "GL-21 Grenade Launcher",
                "FX-12 Shield Generator Relay",
                "FLAM-40 Flamethrower",
                "FAF-14 Spear",
                "EXO-49 Emancipator Exosuit",
                "EXO-45 Patriot Exosuit",
                "EAT-17 Expendable Anti-Tank",
                "Eagle Strafing Run",
                "Eagle Smoke Strike",
                "Eagle Napalm Airstrike",
                "Eagle Cluster Bomb",
                "Eagle Airstrike",
                "Eagle 500kg Bomb",
                "Eagle 110mm Rocket Pods",
                "E/MG-101 HMG Emplacement",
                "E/AT-12 Anti-Tank Emplacement",
                "B-100 Portable Hellbomb",
                "B-1 Supply Pack",
                "AX/TX-13 \"Guard Dog\" Dog Breath",
                "AX/LAS-5 \"Guard Dog\" Rover",
                "AX/AR-23 \"Guard Dog\"",
                "ARC-3 Arc Thrower",
                "APW-1 Anti-Materiel Rifle",
                "AC-8 Autocannon",
                "A/MLS-4X Rocket Sentry",
                "A/MG-43 Machine Gun Sentry",
                "A/M-23 EMS Mortar Sentry",
                "A/M-12 Mortar Sentry",
                "A/G-16 Gatling Sentry",
                "A/FLAM-40 Flame Sentry",
                "A/ARC-3 Tesla Tower",
                "A/AC-8 Autocannon Sentry",
                "Reinforce",
                "SoS Beacon",
                "Resupply",
                "Eagle Rearm",
                "SSSD Delivery",
                "Prospecting Drill",
                "Super Earth Flag",
                "Hellbomb",
                "Upload Data",
                "Seismic Probe",
                "Orbital Illumination Flare (Stratagem Hero Game only)",
                "SEAF Artillery",
                "Dark Fluid Vessel",
                "Tectonic Drill",
                "Hive Breaker Drill"
        };
        Arrays.sort(stratagemarray);

        return stratagemarray;
    }
    public static String stratagemArrows(String choice){
        //https://helldivers.wiki.gg/wiki/Stratagems
        String left  = "\u21E6";
        String right = "\u21E8";
        String up    = "\u21E7";
        String down  = "\u21E9";
        String arrows = "";
        switch (choice){
            case "TX-41 Sterilizer" -> arrows = down+left+up+down+left;
            case "StA-X3 W.A.S.P. Launcher" -> arrows = down+down+up+down+right;
            case "SH-51 Directional Shield" -> arrows = down +up+left+right+up+up;
            case "SH-32 Shield Generator Pack" -> arrows = down+up+left+right+left+right;
            case "SH-20 Ballistic Shield Backpack" -> arrows = down+left+down+down+up+left;
            case "RS-422 Railgun" -> arrows = down+right+down+up+left+right;
            case "RL-77 Airburst Rocket Launcher" -> arrows = down+up+up+left+right;
            case "Orbital Walking Barrage" -> arrows = right+down+right+down+right+down;
            case "Orbital Smoke Strike" -> arrows = right+right+down+up;
            case "Orbital Railcannon Strike" -> arrows = right+up+down+down+right;
            case "Orbital Precision Strike" -> arrows = right+right+up;
            case "Orbital Napalm Barrage" -> arrows = right+right+down+left+right+up;
            case "Orbital Laser" -> arrows = right+down+up+right+down;
            case "Orbital Gatling Barrage" -> arrows = right+down+left+up+up;
            case "Orbital Gas Strike" -> arrows = right+right+down+right;
            case "Orbital EMS Strike" -> arrows = right+right+left+down;
            case "Orbital Airburst Strike" -> arrows = right+right+right;
            case "Orbital 380mm HE Barrage" -> arrows = right+down+up+up+left+down+down;
            case "Orbital 120mm HE Barrage" -> arrows = right+right+down+left+right+down;
            case "MLS-4X Commando" -> arrows = down+left+up+down+right;
            case "MG-206 Heavy Machine Gun" -> arrows = down+left+up+down+down;
            case "MG-43 Machine Gun" -> arrows = down+left+down+up+right;
            case "MD-I4 Incendiary Mines" -> arrows = down+left+left+down;
            case "MD-17 Anti-Tank Mines" -> arrows = down+left+up+up;
            case "MD-8 Gas Mines" -> arrows = down+left+left+right;
            case "MD-6 Anti-Personnel Minefield" -> arrows = down+left+up+right;
            case "M-105 Stalwart" -> arrows = down+left+down+up+up+left;
            case "M-102 Fast Recon Vehicle" -> arrows = left+down+right+down+right+down+up;
            case "LIFT-850 Jump Pack" -> arrows = down+up+up+down+up;
            case "LAS-99 Quasar Cannon" -> arrows = down+down+up+left+right;
            case "LAS-98 Laser Cannon" -> arrows = down+left+down+up+left;
            case "GR-8 Recoilless Rifle" -> arrows = down+left+right+right+left;
            case "GL-21 Grenade Launcher" -> arrows = down+left+up+left+down;
            case "FX-12 Shield Generator Relay" -> arrows = down+down+left+right+left+right;
            case "FLAM-40 Flamethrower" -> arrows = down+left+up+down+up;
            case "FAF-14 Spear" -> arrows = down+down+up+down+down;
            case "EXO-49 Emancipator Exosuit" -> arrows = left+down+right+up+left+down+up;
            case "EXO-45 Patriot Exosuit" -> arrows = left+down+right+up+left+down+down;
            case "EAT-17 Expendable Anti-Tank" -> arrows = down+down+left+up+right;
            case "Eagle Strafing Run" -> arrows = up+right+right;
            case "Eagle Smoke Strike" -> arrows = up+right+up+down;
            case "Eagle Napalm Airstrike" -> arrows = up+right+down+up;
            case "Eagle Cluster Bomb" -> arrows = up+right+down+down+right;
            case "Eagle Airstrike" -> arrows = up+right+down+right;
            case "Eagle 500kg Bomb" -> arrows = up+right+down+down+down;
            case "Eagle 110mm Rocket Pods" -> arrows = up+right+up+left;
            case "E/MG-101 HMG Emplacement" -> arrows = down+up+left+right+right+left;
            case "E/AT-12 Anti-Tank Emplacement" -> arrows = down+up+left+right+right+right;
            case "B-100 Portable Hellbomb" -> arrows =  down+right+up+up+up;
            case "B-1 Supply Pack" -> arrows = down+left+down+up+up+down;
            case "AX/TX-13 \"Guard Dog\" Dog Breath" -> arrows = down+up+left+up+right+up;
            case "AX/LAS-5 \"Guard Dog\" Rover" -> arrows = down+up+left+up+right+right;
            case "AX/AR-23 \"Guard Dog\"" -> arrows = down+up+left+up+right+down;
            case "ARC-3 Arc Thrower" -> arrows = down+right+down+up+left+left;
            case "APW-1 Anti-Materiel Rifle" -> arrows = down+left+right+up+down;
            case "AC-8 Autocannon" -> arrows = down +left+down+up+up+right;
            case "A/MLS-4X Rocket Sentry" -> arrows = down+up+right+right+left;
            case "A/MG-43 Machine Gun Sentry" -> arrows = down+up+right+right+up;
            case "A/M-23 EMS Mortar Sentry" -> arrows = down+up+right+down+right;
            case "A/M-12 Mortar Sentry" -> arrows = down+up+right+right+down;
            case "A/G-16 Gatling Sentry" -> arrows = down+up+right+left;
            case "A/FLAM-40 Flame Sentry" -> arrows = down+up+right+down+up+up;
            case "A/ARC-3 Tesla Tower" -> arrows = down+up+right+up+left+right;
            case "A/AC-8 Autocannon Sentry" -> arrows = down+up+right+up+left+up;
            case "Reinforce" -> arrows = up+down+right+left+up;
            case "SoS Beacon" -> arrows = up+down+right+up;
            case "Resupply" -> arrows = down+down+up+right;
            case "Eagle Rearm" -> arrows = up+up+left+up+right;
            case "SSSD Delivery" -> arrows = down+down+down+up+up;
            case "Prospecting Drill" -> arrows = down+down+left+right+down+down;
            case "Super Earth Flag" -> arrows = up+down+up+down;
            case "Hellbomb" -> arrows = down+up+left+down+up+right+down+up;
            case "Upload Data" -> arrows = left+right+up+up+up;
            case "Seismic Probe" -> arrows = up+up+left+right+down+down;
            case "Orbital Illumination Flare (Stratagem Hero Game only)" -> arrows = right+right+left+left;
            case "SEAF Artillery" -> arrows = right+up+up+down;
            case "Dark Fluid Vessel" -> arrows = up+left+right+down+up+up;
            case "Tectonic Drill" -> arrows = up+down+up+down+up+down;
            case "Hive Breaker Drill" -> arrows = left+up+down+right+down+down;
        }
        return arrows;
    }
    public static String Randomstratagem(List<String> stratagemList){
        Random random = new Random();
        int ranNum = random.nextInt(stratagemList.size());
        return stratagemList.get(ranNum).replace("[","").replace("]","");
    }


}
