package com.example.ea3;

// Java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapdata {
    public static Map<String, List<String>> getRegionMap() {
        Map<String, List<String>> regionMap = new HashMap<>();

        List<String> hkIslandDistricts = new ArrayList<>();
        hkIslandDistricts.add("Central and Western District");
        hkIslandDistricts.add("Eastern District");
        hkIslandDistricts.add("Southern District");
        hkIslandDistricts.add("Wan Chai District");
        regionMap.put("hkIslandDistricts", hkIslandDistricts);

        List<String> kowloonDistricts = new ArrayList<>();
        kowloonDistricts.add("Kowloon City District");
        kowloonDistricts.add("Kwun Tong District");
        kowloonDistricts.add("Sham Shui Po District");
        kowloonDistricts.add("Wong Tai Sin District");
        kowloonDistricts.add("Yau Tsim Mong District");
        regionMap.put("Kowloon Region", kowloonDistricts);

        List<String> newTerritoriesDistricts = new ArrayList<>();
        newTerritoriesDistricts.add("Islands District");
        newTerritoriesDistricts.add("Kwai Tsing District");
        newTerritoriesDistricts.add("North District");
        newTerritoriesDistricts.add("Sai Kung District");
        newTerritoriesDistricts.add("Sha Tin District");
        newTerritoriesDistricts.add("Tai Po District");
        newTerritoriesDistricts.add("Tsuen Wan District");
        newTerritoriesDistricts.add("Tuen Mun District");
        newTerritoriesDistricts.add("Yuen Long District");
        regionMap.put("New Territories Region", newTerritoriesDistricts);

        return regionMap;
    }

    public static Map<String, List<Stadium>> getDistrictStadiumMap() {
        Map<String, List<Stadium>> districtStadiumMap = new HashMap<>();
        List<Stadium> centralAndWesternStadiums = new ArrayList<>();
        centralAndWesternStadiums.add(new Stadium("Hong Kong Park Sports Centre", R.drawable.hk, "https://maps.google.com/?q=香港公園體育館"));
        centralAndWesternStadiums.add(new Stadium("Hong Kong Squash Centre", R.drawable.hks, "https://maps.google.com/?q=香港壁球中心"));
        centralAndWesternStadiums.add(new Stadium("Shek Tong Tsui Sports Centre", R.drawable.stsc, "https://maps.google.com/?q=石塘咀體育館"));
        centralAndWesternStadiums.add(new Stadium("Sheung Wan Sports Centre", R.drawable.swsc, "https://maps.google.com/?q=上環體育館"));
        centralAndWesternStadiums.add(new Stadium("Shum Mun Fong Sports Centre", R.drawable.smafc, "https://maps.google.com/?q=士美非路體育館"));
        centralAndWesternStadiums.add(new Stadium("Dr. Sun Yat-sen Memorial Park Sports Centre", R.drawable.dsysmpsc, "https://maps.google.com/?q=中山紀念公園體育館"));
        districtStadiumMap.put("Central and Western District", centralAndWesternStadiums);

        List<Stadium> easternStadiums = new ArrayList<>();
        easternStadiums.add(new Stadium("Chai Wan Sports Centre", R.drawable.cwsc, "https://maps.google.com/?q=柴灣體育館"));
        easternStadiums.add(new Stadium("Island East Sports Centre", R.drawable.iesc, "https://maps.google.com/?q=港島東體育館"));
        easternStadiums.add(new Stadium("Java Road Sports Centre", R.drawable.jrsc, "https://maps.google.com/?q=渣華道體育館"));
        easternStadiums.add(new Stadium("Quarry Bay Sports Centre", R.drawable.qbsc, "https://maps.google.com/?q=鰂魚涌體育館"));
        easternStadiums.add(new Stadium("Sai Wan Ho Sports Centre", R.drawable.swhc, "https://maps.google.com/?q=西灣河體育館"));
        easternStadiums.add(new Stadium("Siu Sai Wan Sports Centre", R.drawable.iesc, "https://maps.google.com/?q=小西灣體育館籃球場"));
        districtStadiumMap.put("Eastern District", easternStadiums);

        List<Stadium> southernStadiums = new ArrayList<>();
        southernStadiums.add(new Stadium("Aberdeen Sports Centre", R.drawable.asc, "https://maps.google.com/?q=香港仔體育館"));
        southernStadiums.add(new Stadium("Aberdeen Tennis and Squash Centre", R.drawable.atasc1, "https://maps.google.com/?q=香港仔網球及壁球中心"));
        southernStadiums.add(new Stadium("Ap Lei Chau Sports Centre", R.drawable.alcsc, "https://maps.google.com/?q=鴨脷洲體育館"));
        southernStadiums.add(new Stadium("Stanley Sports Centre", R.drawable.ssc, "https://maps.google.com/?q=赤柱體育館"));
        southernStadiums.add(new Stadium("Wong Chuk Hang Sports Centre", R.drawable.wchsc, "https://maps.google.com/?q=黃竹坑體育館"));
        southernStadiums.add(new Stadium("Yue Kwong Road Sports Centre", R.drawable.ykrsc, "https://maps.google.com/?q=漁光道體育館"));
        districtStadiumMap.put("Southern District", southernStadiums);

        List<Stadium> wanChaiStadiums = new ArrayList<>();
        wanChaiStadiums.add(new Stadium("Harbour Road Sports Centre", R.drawable.hrsc, "https://maps.google.com/?q=摩理臣山道體育館"));
        wanChaiStadiums.add(new Stadium("Lockhart Road Sports Centre", R.drawable.lrsc, "https://maps.google.com/?q=駱克道體育館"));
        wanChaiStadiums.add(new Stadium("Wong Nai Chung Sports Centre", R.drawable.wncsc, "https://maps.google.com/?q=黃泥涌體育館"));
        districtStadiumMap.put("Wan Chai District", wanChaiStadiums);

        List<Stadium> kowloonCityStadiums = new ArrayList<>();
        kowloonCityStadiums.add(new Stadium("Hung Hom Municipal Services Building Sports Centre", R.drawable.hhmss, "https://maps.google.com/?q=紅磡市政大廈體育館"));
        kowloonCityStadiums.add(new Stadium("Fat Kwong Street Sports Centre", R.drawable.fwssc, "https://maps.google.com/?q=佛光街體育館"));
        kowloonCityStadiums.add(new Stadium("Ho Man Tin Sports Centre", R.drawable.hmtsc, "https://maps.google.com/?q=何文田體育館"));
        kowloonCityStadiums.add(new Stadium("Kowloon City Sports Centre", R.drawable.kcsc1, "https://maps.google.com/?q=九龍城體育館"));
        kowloonCityStadiums.add(new Stadium("To Kwa Wan Sports Centre", R.drawable.tkwsc, "https://maps.google.com/?q=土瓜灣體育館"));
        districtStadiumMap.put("Kowloon City District", kowloonCityStadiums);

        List<Stadium> kwunTongStadiums = new ArrayList<>();
        kwunTongStadiums.add(new Stadium("Choi Wing Road Sports Centre", R.drawable.sspsc, "https://maps.google.com/?q=彩榮路體育館"));
        kwunTongStadiums.add(new Stadium("Chun Wah Road Sports Centre", R.drawable.cwrsc, "https://maps.google.com/?q=振華道體育館"));
        kwunTongStadiums.add(new Stadium("Hiu Kwong Street Sports Centre", R.drawable.hkssc, "https://maps.google.com/?q=曉光街體育館"));
        kwunTongStadiums.add(new Stadium("Kowloon Bay Sports Centre", R.drawable.kbsc, "https://maps.google.com/?q=九龍灣體育館"));
        kwunTongStadiums.add(new Stadium("Lam Tin South Sports Centre", R.drawable.ltssc, "https://maps.google.com/?q=藍田(南)體育館"));
        kwunTongStadiums.add(new Stadium("Lei Yue Mun Sports Centre", R.drawable.lymsc, "https://maps.google.com/?q=鯉魚門體育館"));
        kwunTongStadiums.add(new Stadium("Ngau Tau Kok Road Sports Centre", R.drawable.ntkrsc, "https://maps.google.com/?q=牛頭角道體育館"));
        kwunTongStadiums.add(new Stadium("Shui Wo Street Sports Centre", R.drawable.swssc, "https://maps.google.com/?q=瑞和街體育館"));
        kwunTongStadiums.add(new Stadium("Shun Lee Tsuen Sports Centre", R.drawable.sltsc, "https://maps.google.com/?q=順利邨體育館"));
        districtStadiumMap.put("Kwun Tong District", kwunTongStadiums);

        List<Stadium> shamShuiPoStadiums = new ArrayList<>();
        shamShuiPoStadiums.add(new Stadium("Cheung Sha Wan Sports Centre", R.drawable.cswsc, "https://maps.google.com/?q=長沙灣體育館"));
        shamShuiPoStadiums.add(new Stadium("Lai Chi Kok Park Sports Centre", R.drawable.lckpsc, "https://maps.google.com/?q=荔枝角公園體育館"));
        shamShuiPoStadiums.add(new Stadium("Cornwall Street Squash and Table Tennis Centre", R.drawable.cssattc, "https://maps.google.com/?q=歌和老街壁球及乒乓球中心"));
        shamShuiPoStadiums.add(new Stadium("Pei Ho Street Sports Centre", R.drawable.phssc, "https://maps.google.com/?q=北河街體育館"));
        shamShuiPoStadiums.add(new Stadium("Po On Road Sports Centre", R.drawable.porsc, "https://maps.google.com/?q=保安道體育館"));
        shamShuiPoStadiums.add(new Stadium("Sham Shui Po Sports Centre", R.drawable.sssc, "https://maps.google.com/?q=深水埗體育館"));
        shamShuiPoStadiums.add(new Stadium("Shek Kip Mei Park Sports Centre", R.drawable.skmpsc, "https://maps.google.com/?q=石硤尾公園體育館"));
        shamShuiPoStadiums.add(new Stadium("Tung Chau Street Park Squash Centre", R.drawable.tcspsc, "https://maps.google.com/?q=通州街公園壁球中心"));
        districtStadiumMap.put("Sham Shui Po District", shamShuiPoStadiums);

        List<Stadium> wongTaiSinStadiums = new ArrayList<>();
        wongTaiSinStadiums.add(new Stadium("Choi Hung Road Sports Centre", R.drawable.chrsc, "https://maps.google.com/?q=彩虹道體育館"));
        wongTaiSinStadiums.add(new Stadium("Choi Hung Road Badminton Centre", R.drawable.chrbc, "https://maps.google.com/?q=彩虹道羽毛球中心"));
        wongTaiSinStadiums.add(new Stadium("Chuk Yuen Sports Centre", R.drawable.cysc, "https://maps.google.com/?q=竹園體育館"));
        wongTaiSinStadiums.add(new Stadium("Kai Tak East Sports Centre", R.drawable.ktesc, "https://maps.google.com/?q=東啟德體育館"));
        wongTaiSinStadiums.add(new Stadium("Morse Park Sports Centre", R.drawable.mpsc, "https://maps.google.com/?q=摩士公園體育館"));
        wongTaiSinStadiums.add(new Stadium("Ngau Chi Wan Sports Centre", R.drawable.ncwsc, "https://maps.google.com/?q=牛池灣體育"));
        wongTaiSinStadiums.add(new Stadium("Po Kong Village Road Sports Centre", R.drawable.pkvrsc, "https://maps.google.com/?q=蒲崗村道體育館"));
        districtStadiumMap.put("Wong Tai Sin District", wongTaiSinStadiums);

        List<Stadium> yauTsimMongStadiums = new ArrayList<>();
        yauTsimMongStadiums.add(new Stadium("Fa Yuen Street Sports Centre", R.drawable.kpsc, "https://maps.google.com/?q=花園街體育館"));
        yauTsimMongStadiums.add(new Stadium("Kowloon Park Sports Centre", R.drawable.kpsc, "https://maps.google.com/?q=九龍公園體育館"));
        yauTsimMongStadiums.add(new Stadium("Boundary Street Sports Centre No. 1", R.drawable.bsscn1, "https://maps.google.com/?q=界限街一號體育館"));
        yauTsimMongStadiums.add(new Stadium("Boundary Street Sports Centre No. 2", R.drawable.bsscn2, "https://maps.google.com/?q=界限街二號體育館"));
        yauTsimMongStadiums.add(new Stadium("Kwun Chung Sports Centre", R.drawable.kcsc1, "https://maps.google.com/?q=官涌體育館"));
        yauTsimMongStadiums.add(new Stadium("Tai Kok Tsui Sports Centre", R.drawable.tktsc, "https://maps.google.com/?q=大角咀體育館"));
        districtStadiumMap.put("Yau Tsim Mong District", yauTsimMongStadiums);

        List<Stadium> islandsStadiums = new ArrayList<>();
        islandsStadiums.add(new Stadium("Cheung Chau Sports Centre", R.drawable.ccsc, "https://maps.google.com/?q=長洲體育館"));
        islandsStadiums.add(new Stadium("Mui Wo Sports Centre", R.drawable.mwsc, "https://maps.google.com/?q=梅窩體育館"));
        islandsStadiums.add(new Stadium("Peng Chau Sports Centre", R.drawable.pcsc, "https://maps.google.com/?q=坪洲體育館"));
        islandsStadiums.add(new Stadium("Praya Street Sports Centre", R.drawable.pssc, "https://maps.google.com/?q=海傍街體育館"));
        islandsStadiums.add(new Stadium("Tung Chung Man Tung Road Sports Centre", R.drawable.tcmtr, "https://maps.google.com/?q=東涌文東路體育館"));
        districtStadiumMap.put("Islands District", islandsStadiums);


        List<Stadium> kwaiTsingStadiums = new ArrayList<>();
        kwaiTsingStadiums.add(new Stadium("Tsing Yi Southwest Sports Centre", R.drawable.tyssw, "https://maps.google.com/?q=青衣西南體育館"));
        kwaiTsingStadiums.add(new Stadium("Tsing Yi Sports Centre", R.drawable.tysc, "https://maps.google.com/?q=青衣體育館"));
        kwaiTsingStadiums.add(new Stadium("Cheung Fat Sports Centre", R.drawable.cfsc, "https://maps.google.com/?q=長發體育館"));
        kwaiTsingStadiums.add(new Stadium("Fung Shue Wo Sports Centre", R.drawable.fswfc, "https://maps.google.com/?q=楓樹窩體育館"));
        kwaiTsingStadiums.add(new Stadium("Lai King Sports Centre", R.drawable.lksc, "https://maps.google.com/?q=荔景體育館"));
        kwaiTsingStadiums.add(new Stadium("North Kwai Chung Tang Shiu Kin Sports Centre", R.drawable.nkctsksc, "https://maps.google.com/?q=北葵涌鄧肇堅體育館"));
        kwaiTsingStadiums.add(new Stadium("Osman Ramju Sadick Memorial Sports Centre", R.drawable.orsmsc, "https://maps.google.com/?q=林士德體育館"));
        kwaiTsingStadiums.add(new Stadium("Tai Wo Hau Sports Centre", R.drawable.twhsc, "https://maps.google.com/?q=大窩口體育館"));
        districtStadiumMap.put("Kwai Tsing District", kwaiTsingStadiums);

        List<Stadium> northStadiums = new ArrayList<>();
        northStadiums.add(new Stadium("Luen Wo Hui Sports Centre", R.drawable.lwchsc, "https://maps.google.com/?q=聯和墟體育館"));
        northStadiums.add(new Stadium("Lung Sum Avenue Sports Centre", R.drawable.lsasc, "https://maps.google.com/?q=龍琛路體育館"));
        northStadiums.add(new Stadium("Po Wing Road Sports Centre", R.drawable.pwrsc, "https://maps.google.com/?q=保榮路體育館"));
        northStadiums.add(new Stadium("Tin Ping Sports Centre", R.drawable.tpsc, "https://maps.google.com/?q=天平體育館"));
        northStadiums.add(new Stadium("Wo Hing Sports Centre", R.drawable.whsc, "https://maps.google.com/?q=和興體育館"));
        districtStadiumMap.put("North District", northStadiums);

        List<Stadium> saiKungStadiums = new ArrayList<>();
        saiKungStadiums.add(new Stadium("Hang Hau Sports Centre", R.drawable.hhsc, "https://maps.google.com/?q=坑口體育館"));
        saiKungStadiums.add(new Stadium("Hong Kong Velodrome", R.drawable.hks, "https://maps.google.com/?q=香港單車館"));
        saiKungStadiums.add(new Stadium("Po Lam Sports Centre", R.drawable.plsc, "https://maps.google.com/?q=寶林體育館"));
        saiKungStadiums.add(new Stadium("Sai Kung Squash Courts", R.drawable.sksc, "https://maps.google.com/?q=西貢壁球場"));
        saiKungStadiums.add(new Stadium("Tiu Keng Leng Sports Centre", R.drawable.tklsc, "https://maps.google.com/?q=調景嶺體育館"));
        saiKungStadiums.add(new Stadium("Tseung Kwan O Sports Centre", R.drawable.tk0sc, "https://maps.google.com/?q=將軍澳體育館"));
        saiKungStadiums.add(new Stadium("Tsui Lam Sports Centre", R.drawable.tlsc, "https://maps.google.com/?q=翠林體育館"));
        districtStadiumMap.put("Sai Kung District", saiKungStadiums);

        List<Stadium> shaTinStadiums = new ArrayList<>();
        shaTinStadiums.add(new Stadium("Che Kung Temple Sports Centre", R.drawable.cktsc, "https://maps.google.com/?q=車公廟體育館"));
        shaTinStadiums.add(new Stadium("Heng On Sports Centre ", R.drawable.hosc, "https://maps.google.com/?q=恆安體育館"));
        shaTinStadiums.add(new Stadium("Hin Keng Sports Centre", R.drawable.hksc, "https://maps.google.com/?q=顯徑體育館"));
        shaTinStadiums.add(new Stadium("Ma On Shan Sports Centre", R.drawable.mossc, "https://maps.google.com/?q=馬鞍山體育館"));
        shaTinStadiums.add(new Stadium("Mei Lam Sports Centre", R.drawable.mlsc, "https://maps.google.com/?q=美林體育館"));
        shaTinStadiums.add(new Stadium("Sha Tin Jockey Club Public Squash Courts", R.drawable.stjcpsc, "https://maps.google.com/?q=沙田賽馬會公眾壁球場"));
        shaTinStadiums.add(new Stadium("Yuen Chau Kok Sports Centre", R.drawable.ycksc, "https://maps.google.com/?q=圓洲角體育館"));
        shaTinStadiums.add(new Stadium("Yuen Wo Road Sports Centre", R.drawable.ywrsc, "https://maps.google.com/?q=源禾路體育館"));
        districtStadiumMap.put("Sha Tin District", shaTinStadiums);

        List<Stadium> taiPoStadiums = new ArrayList<>();
        taiPoStadiums.add(new Stadium("Fu Heng Sports Centre", R.drawable.fhsc, "https://maps.google.com/?q=富亨體育館"));
        taiPoStadiums.add(new Stadium("Fu Shin Sports Centre", R.drawable.fssc, "https://maps.google.com/?q=富善體育館"));
        taiPoStadiums.add(new Stadium("Tai Po Hui Sports Centre", R.drawable.tphsc, "https://maps.google.com/?q=大埔墟體育館"));
        taiPoStadiums.add(new Stadium("Tai Po Sports Centre", R.drawable.tpsc1, "https://maps.google.com/?q=大埔體育館"));
        taiPoStadiums.add(new Stadium("Tai Wo Sports Centre", R.drawable.twsc, "https://maps.google.com/?q=太和體育館"));
        taiPoStadiums.add(new Stadium("Tung Cheong Street Sports Centre", R.drawable.tcssc, "https://maps.google.com/?q=東昌街體育館"));
        districtStadiumMap.put("Tai Po District", taiPoStadiums);

        List<Stadium> tsuenWanStadiums = new ArrayList<>();
        tsuenWanStadiums.add(new Stadium("Tsuen King Circuit Sports Centre", R.drawable.tkcsc, "https://maps.google.com/?q=荃景圍體育館"));
        tsuenWanStadiums.add(new Stadium("Tsuen Wan Sports Centre", R.drawable.twsc1, "https://maps.google.com/?q=荃灣體育館"));
        tsuenWanStadiums.add(new Stadium("Tsuen Wan West Sports Centre", R.drawable.twwsc, "https://maps.google.com/?q=荃灣西約體育館"));
        tsuenWanStadiums.add(new Stadium("Wai Tsuen Sports Centre", R.drawable.twwsc, "https://maps.google.com/?q=蕙荃體育館"));
        tsuenWanStadiums.add(new Stadium("Yeung Uk Road Sports Centre", R.drawable.yursc, "https://maps.google.com/?q=楊屋道體育館"));
        districtStadiumMap.put("Tsuen Wan District", tsuenWanStadiums);

        List<Stadium> tuenMunStadiums = new ArrayList<>();
        tuenMunStadiums.add(new Stadium("Leung Tin Sports Centre", R.drawable.lssc, "https://maps.google.com/?q=良田體育館"));
        tuenMunStadiums.add(new Stadium("Siu Lun Sports Centre", R.drawable.slsc, "https://maps.google.com/?q=兆麟體育館"));
        tuenMunStadiums.add(new Stadium("Tai Hing Sports Centre", R.drawable.thsc, "https://maps.google.com/?q=大興體育館"));
        tuenMunStadiums.add(new Stadium("The Jockey Club Tuen Mun Butterfly Beach Sports Centre", R.drawable.tjctmbbsc, "https://maps.google.com/?q=賽馬會屯門蝴蝶灣體育館"));
        tuenMunStadiums.add(new Stadium("TUEN MUN SWIMMING POOL SQUASH COURTS", R.drawable.tmspsc, "https://maps.google.com/?q=屯門游泳池壁球場"));
        tuenMunStadiums.add(new Stadium("Yau Oi Sports Centre", R.drawable.yosc, "https://maps.google.com/?q=友愛體育館"));
        districtStadiumMap.put("Tuen Mun District", tuenMunStadiums);

        List<Stadium> yuenLongStadiums = new ArrayList<>();
        yuenLongStadiums.add(new Stadium("Fung Kam Street Sports Centre", R.drawable.fkssc, "https://maps.google.com/?q=鳳琴街體育館"));
        yuenLongStadiums.add(new Stadium("Long Ping Sports Centre", R.drawable.lpsc, "https://maps.google.com/?q=朗屏體育館"));
        yuenLongStadiums.add(new Stadium("Ping Shan Tin Shui Wai Sports Centre", R.drawable.pstswsc, "https://maps.google.com/?q=屏山天水圍體育館"));
        yuenLongStadiums.add(new Stadium("Tin Fai Road Sports Centre", R.drawable.tfrsc, "https://maps.google.com/?q=天暉路體育館"));
        yuenLongStadiums.add(new Stadium("Tin Shui Sports Centre", R.drawable.tssc, "https://maps.google.com/?q=天瑞體育館"));
        yuenLongStadiums.add(new Stadium("Tin Shui Wai Sports Centre", R.drawable.tswsc, "https://maps.google.com/?q=天水圍體育館"));
        yuenLongStadiums.add(new Stadium("Yuen Long Sports Centre", R.drawable.ylsc, "https://maps.google.com/?q=元朗體育館"));
        districtStadiumMap.put("Yuen Long District", yuenLongStadiums);

        return districtStadiumMap;
    }
}
