package com.banerjee.spellingbee.service;

import com.banerjee.spellingbee.dto.SuccessfulResponseDTO;
import com.banerjee.spellingbee.dto.WordDTO;
import com.banerjee.spellingbee.integration.WebsterApiClient;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by somobanerjee on 2/26/17.
 */
@Service
public class WordWriterService {

    @Autowired
    MongoDatabase mongoDatabase;

    @Autowired
    WebsterApiClient websterApiClient;
    private static final Logger log = LoggerFactory.getLogger(WordWriterService.class);
    /*private static String WORDS = "abalone,ablution,abominate,absolution,abstemious,academe,accolade,acculturation,accumbent,acme,acolyte,acrolect,admonish,"
        + "adobo,Adonis,aerodynamic,affiliate,agathism,agonal,agrarianism,alchemy,aldosterone,alee,alfalfa,alkaline,alkane,allopelagic,"
        + ",allotropic,alpaca,altarpiece,amandine,amenable,amicable,amigo,amphidromic,anachronism,anamnesis,anatomy,ancestor,ancient,"
        + ",ancilla,anemone,angst,angstrom,animism,annihilation,antagonist,ante,antiquarian,antiquity,apathy,aphasia,apocalyptic,"
        + "Apollonian,apostate,apostle,apotheosis,approbation,arboriculture,archangel,argonaut,arietta,aristocracy,arraign,artichoke,"
        + ",artillery,aspic,assize,assurgent,astrologer,asynchrony,atmogenic,atrocious,attaboy,audience,aureate,aureole,axel,axiom,"
        + "ayatollah,Aztec,backgammon,badminton,baleen,ballotage,barophilic,baroque,barrable,barrage,barrio,basilisk,bathybic,beadle,"
        + "beige,belladonna,belle,benefactor,benevolence,benign,bequeath,bereavement,bethel,Bhagavat,bibelot,bibliotherapy,bicaudate,"
        + "biennium,billycan,binary,blanquette,blatherskite,blintze,bloc,bolivar,bolshevize,bonification,bonito,bowgrace,brabble,"
        + "braggadocio,brevet,brigadier,brocade,bulla,Bunyanesque,buttress,caballero,cabana,cadences,calamity,calcaneus,caldera,"
        + "calefactory,calisthenics,calypso,cameo,camouflage,cantata,cantonment,cantor,capillary,capitulation,caramelize,carcinogenic,"
        + "caribou,carmine,carotid,carrion,caruncle,casserole,caste,caucus,cauliflower,censurable,cesspool,chagrin,chalupa,chancellor,"
        + "changa,chaos,charismatic,charlatan,chauvinism,chenille,chimerical,chisel,chistera,chlorine,cholesterol,chorus,chromic,"
        + "chromosome,chronic,chrysalis,cilantro,circuit,circulus,clerisy,coagulate,coaler,cobalt,cochlear,codicil,coexistence,"
        + "collateral,collation,collocate,combustible,commissary,committal,concerto,concession,concurrent,condign,condiment,condolences,"
        + ",congenital,connoisseur,conquistador,consolidationist,constituency,contemn,contemporaneous,contraband,conversant,cooncan,"
        + "copepod,cornucopia,correlative,corrugated,corundum,cossack,coupe,cowl,cravat,cribbage,crimson,crochet,crouton,crucible,"
        + "crumpet,cryology,cuisine,Culex,culvert,cupola,cybernetics,Cyclops,cyclorama,cylindrical,cynical,cynology,cytology,cytoplasm,"
        + "dactyl,dandruff,dative,decamerous,decathlon,decrepitude,decuple,deltiology,deluge,demersal,demijohn,dermatology,"
        + "desertification,despoilment,despotic,destitute,détente,deterrence,detrimental,deuce,diastolic,diatribe,dichotomy,didactic,"
        + "dieselize,dirge,disafforest,discern,disembowel,disfranchise,disinterred,dissect,dissimilitude,dissolution,dissymmetry,"
        + "distillation,dittology,diversionary,donzella,dossier,doxology,dramaturgy,drollery,dyslexiaeatage,ecclesiastic,echt,ecocide,"
        + "edifices,efficacy,efflorescence,effuse,electorally,emaciation,embalmment,embergoose,emulsify,enalid,encore,encroachment,endothermic,"
        + "enepidermic,entourage,epibenthos,epidemiology,epithelial,eponym,equestrian,equilibrium,eremology,ersatz,erstwhile,erudite,"
        + "estuarine,ethnarch,eulogistic,eulogy,eupepsia,euphonious,euphoria,eupraxia,Europeanism,exchequer,excruciating,execrable,"
        + "exhume,exothermally,expeditionary,expeditious,expiate,exposition,expressionism,extant,extemporaneous,extinguishant,exuberant";*/

/*    private static String WORDS = "falsetto,fandango,fastidious,febricula,fennel,ferret,fiduciary,filamentary,filmography,finial,"
        + "firth,fistula,flamenco,flanger,flautino,fleabane,floe,flotsam,flouncing,foliation,folksiness,foreland,forensics,formidable,"
        + "forsythia,friar,fritter,frolicsome,fronds,fronton,fulmination,funereal,furlong,fuselage,fusilli,futon,gadroon,galvanic,"
        + "ganglion,gardenia,gastroenterology,gavel,gazelle,geisha,gender,genealogy,genteel,genuflect,genus,geochrony,geriatrics,"
        + "Geronimo,gerontology,gingham,gird,gladiator,glossal,glucocorticoid,glycerol,glycogen,gnats,goatee,gondolet,gordian,gorgonize,"
        + "gorse,graupel,gristle,grotto,gubernatorial,guillotine,gulag,gunnery,guru"
        + ",gyrocar,hacienda,halieutic,hamartia,Harlequin,heathen,hecatomb,hegemony,heifer,hellenic,hematology,heptad,hexapod,hibernal,"
        + "hippodrome,hippology,hirtellous,histrionics,homologate,hookaroon,horde,hoyle,humic,hustings,hydronautics,hyetology,"
        + "hygroscopic,hyperbole,hypochondria,hypocorism,hypostasis,hypothalamic,hysterical,ides,"
        + "illimitable,illuminati,immaculate,Immemorial,immunologic,impeccable,impersonator,incendiarism,incised,incoherently,infauna,"
        + "infiltration,influenza,ingenue,iniquitous,initiate,innocuous,inopportune,inorganic,inquinate,installation,instantaneous,"
        + "interceptor,interdiction,interdisciplinary,interim,interject,interment,intermezzo,intermolecular,interpellate,interregnum,"
        + "interstice,intertidal,intimidation,inundation,iota,irascibility,irenicism,irreconcilable,irreligious,irreverent,isochronal,"
        + "isohaline,isotopic,Jacksonian,janiform,jingoism,juniper,junta,justiciable,Kallikak,karma,kathakali,kennel,kenning,keno,"
        + "kestrel,kitsch,kleptocracy,kremlin,Kremlinology,krill,kugel,laity,lama,lamasery,lamprey,lancet,langlauf,larithmics,lathe,"
        + "latticed,lavatory,lederhosen,leotard,lethargic,leviathan,lexicology,lieutenant,ligament,lignite,lineolate,litigious,"
        + "liverwurst,lochan,logistician,logopedics,logorrhea,lumbriciform,lustrum,Lutz,macadamia,Mach,maestro,maggot,maglev,magnanimity,magnolia,mahogany,maladroit,malady,malapert,malaria,malediction,maleficence,"
    + "malfeasance,malice,mallard,malleate,malodorous,malum,manicotti,manna,mantra,manumit,maraschino,maraud,marinorama,marsupium,"
    + "martyrium,materiel,mayonnaise,mechanics,Meistersinger,melancholy,meliorate,membranous,menial,mensal,mensuration,mesocracy,"
    + "metabolize,metachronous,metalloid,metallurgy,meteorology,metronome,militarism,minaret,ministerial,minuend,misogynist";*/

private static final String WORDS = "";/*"misprision,mnemonic,mohair,monaural,mongrel,monotheistic,montage,monte,Mornay,mortician,mosaic,mosquito,mourning,muchacha,"
    + "mulligan,mummify,musette,musicale,mycology,mynheer,myriad,nacelle,narcissistic,nauseous,neckerchief,necromancy,negotiator,"
    + "neritic,netsuke,neuralgia,niagara,nicad,niello,niobium,nobelist,nonce,noncombatant,nonnecessity,noology,norns,noxious,"
    + "nullifidian,numismatics,oarfish,obit,obliged,oblique,obliterate,obrotund,obsequious,obstetrician,obtenebrate,oceanography,"
    + "octopod,oculus,odium,offertory,officious,ogre,oligarchy,olympiad,ombudsman,ominous,omniscient,oncology,oolong,ophthalmic,"
    + "opulent,opusculum,oratorio,ordination,organogeny,orienteering,origami,orthodox,orzo,osmotic,ossuary,ostentation,ostentatious,"
    + "pacification,paleography,pampas,pandit,panegyrical,pansied,pantheon,papa,paparazzo,parachronism,paradigm,paralysis,parapet,"
    + "paratroops,parfait,parry,passel,pathology,pathos,patina,patriarch,patronage,paucity,pavlova,pegasus,pendule,penitent,penlop,"
    + "pennyroyal,pentathlon,periodontal,periphery,peristaltic,pernicious,perpetuity,persimmon,pestilential,pestle,pettifog,"
    + "pharmacognosy,philanthropy,philately,philharmonic,phrontistery,phyllo,piazza,pillion,pinafore,pinata,pippin,piscivorous,"
    + "pituitary,placet,plague,platypus,pliant,pliothermic,plover,plumage,plummeted,plurinominal,plutonian,pneumatic,poignant,polder"
    + ",polemology,polenta,Pollyanna,polyandrium,pomology,ponerology,ponticello,pontiff,porpoise,postmortem,postnuptial,postpartum,"
    + "postprandial,potentate,powwow,prairie,precipice,precocious,predecessor,preemergent,preeminent,prehensile,prenuptial,"
    + "prestidigitator,prestissimo,prioress,priscan,probate,procrastinate,procrustean,profligacy,prognosis,projectionist,propinquity"
    + ",prosaic,prospicience,protagonist,protean,protectorate,protium,provolone,puka,punya,puree,putsch,pyrometallurgical,quern,"
    + "quietus,quinine,ranunculus,readjourn,recalcitrant,recidivist,reciprocal,recitalist,reconciliation,recyclable,refectory,"
    + "regatta,regime,regiment,regisseur,rehabilitation,remedy,repertoire,reprobate,residue,resile,resonance,reticence,rhizome,rive,"
    + "roborant,rococo,rookery,roriferous,rosary,rosette,rotisserie,rotunda,roulette,roundsters,rubella,rudiments,runesmith,runnels,"
    + "rutabaga,tableau,tachometer,tahini,taiga,tandoori,taphonomy,tariff,tarpon,tarragon,tarriance,taupe,taxonomy,tearjerker,"
    + "telemark,televangelist,tenebrosity,termolecular,ternary,terrapin,terrigenous,theriatrics,thermonuclear,thesaurus,thirdborough"
    + ",tiki,tilde,timorously,tortilla,tortoni,totem,tournament,tournedos,toxicology,tracery,tracheal,trajectory,trawl,treacherous,"
    + "trefoil,tremolo,tribology,triceratops,trichotomy,trigonometry,trilobite,tryst,tutelage,ultimatum,ultimo,umlaut,unannotated,"
    + "uncouth,unction,understudy,undulate,unerringly,unimpeachable,unintelligible,unnecessarily,unutterable,usurped,uvula,valence,"
    + "vanadium,vaporetto,varicella,venue,verbena,vernal,versicle,vertebral,vigil,vigilante,vindaloo,vintage,violoncello,viscous,"
    + "vitiate,vomitory,vortex,votary,walleyed,waltz,wamble,whodunit,willowware,wiseacre,woebegone,wrangle,wretchedness,writhe,"
    + "xenial,yew,yttrium,zendo"*/;

    public boolean saveWordToMongo(Document document) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("word");
        /*log.debug(
            String.format("Start inserting word with value : %s", document));*/
        collection.insertOne(document);
        /*log.debug(String.format("Inserted word with value : %s", document));*/
        return true;
    }

    public JSONObject convertWordXMLToJSON(String wordXml) {
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(wordXml);
            //String jsonPrettyPrintString = xmlJSONObj.toString(4);
            //System.out.println(jsonPrettyPrintString);
            return xmlJSONObj;
        } catch (JSONException je) {
            System.out.println(je.toString());
        }
        return null;
    }

    public void saveWords() throws Exception {
        String[] wordArr = WORDS.split(",");
        for(String word : wordArr) {
            saveWord(word);
        }
    }

    private void saveWord(String word) throws Exception {
        String wordXml = websterApiClient.getWordDataFromWebster(word);
        JSONObject wordJson = convertWordXMLToJSON(wordXml);
        saveWordToMongo(Document.parse( wordJson.toString() ));
    }
}
