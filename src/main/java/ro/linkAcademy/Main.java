package ro.linkAcademy;


import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        DbManager con=new DbManager("jdbc:mysql://localhost:3306/bankingsystematm","root","");

        ArrayList<Person> listaPersoane = new ArrayList<>();
        ArrayList<ContBancar> listaConturi = new ArrayList<>();
        HashMap<Person,ArrayList<ContBancar>> totalConturiPersoane = new HashMap<>();


        //UPDATE LISTAPERSOANE CU DATELE DIN DB
        ResultSet rsp = con.executeQuery("Select * from person");
        while(rsp.next()){
            String numedb=rsp.getString("nume");
            String prenumedb=rsp.getString("prenume");
            String cnpdb=rsp.getString("cnp");
            String paroladb=rsp.getString("parola");

            listaPersoane.add(new Person(numedb,prenumedb,cnpdb,paroladb));
        }

        //LOGIN SEQUENCE
        boolean validator = false;
        Map<String, String> listaUseri = new HashMap<>();

        listaUseri.put("user", "parola");
        listaUseri.put("admin", "admin");


        while(true){
            System.out.println("Introdu user si parola(ENTER 4 EXIT):");
            Scanner s = new Scanner(System.in);
            String user = s.nextLine();

            if(user.isEmpty()){
                break;
            }

            String parola = s.nextLine();

            if(parola.isEmpty()){
                break;
            }

            if (listaUseri.containsKey(user)) {
                if (listaUseri.get(user).equals(parola)) {
                    System.out.println("Ai intrat cu succes in cont!");
                    validator = true;
                } else {
                    System.out.println("User sau parola incorecta!");
                }
            }

            //ADMIN SEQUENCE
            if (user.equals("admin")) {
                while (validator) {
                    System.out.println("\n~~~ADMIN PANEL~~~\n" +
                            "\nAlege una dintre optiunile de mai jos:\n" +
                            "1.Inregistreaza o persoana noua\n" +
                            "2.Vezi toate persoanele\n" +
                            "3.Creaza cont nou\n" +
                            "4.Vezi toate conturile\n" +
                            "5.Creeaza/Asigneaza o lista de conturi pentru o persoana\n" +
                            "6.Vezi toti utilizatorii de conturi\n" +
                            "7.Transfera bani\n" +
                            "8.Depune bani\n" +
                            "9.Retrage bani\n" +
                            "10.Inchide program\n");


                    Scanner sc = new Scanner(System.in);
                    int optiune = Integer.valueOf(sc.nextLine());

                    switch (optiune) {
                        case 1:
                            System.out.println("Introdu nume:");
                            String nume = sc.nextLine();
                            System.out.println("Introdu prenume:");
                            String prenume = sc.nextLine();
                            System.out.println("Introdu CNP:");
                            String cnp = sc.nextLine();
                            System.out.println("Introdu parola contului:");
                            String parola1 = sc.nextLine();

                            listaPersoane.add(new Person(nume,prenume,cnp,parola1));
                            listaUseri.put(cnp,parola1);    //ADAUGA CNP SI PAROLA IN SECVENTA DE LOGIN
                            con.insertPersonDB(nume,prenume,cnp,parola1); //ADAUGA USER IN DB
                            System.out.println("Ai inregistrat urmatoarea persoana: "+nume+" "+prenume+" (CNP: "+cnp+")");
                            break;

                        case 2:
                            int counter=1;

                            if(!listaPersoane.isEmpty()){
                                for(Person person:listaPersoane){
                                    System.out.println(counter+". "+person);
                                    counter++;
                                }
                            }else{
                                System.out.println("Lista este goala!");
                            }
                            break;

                        case 3:
                            Random x=new Random();
                            System.out.println("Introdu balanta:");
                            int balanta = Integer.valueOf(sc.nextLine());
                            System.out.println("Introdu tipul de cont(debit/credit/cumparaturi)");
                            String tipCont=sc.nextLine();

                            if(tipCont.toLowerCase().equals("debit") ||
                                    tipCont.toLowerCase().equals("credit") ||
                                    tipCont.toLowerCase().equals("cumparaturi")){
                                listaConturi.add(new ContBancar("RO"+x.nextLong(),balanta,tipCont));
                            }else{
                                System.out.println("Tipul de cont ales nu este valid!");
                            }
                            break;

                        case 4:
                            int counter2=1;

                            if(!listaConturi.isEmpty()){
                                for(ContBancar cont:listaConturi){
                                    System.out.println(counter2+". "+cont);
                                }
                            }else {
                                System.out.println("Nu este inregistrat nici un cont!");
                            }
                            break;

                        case 5:
                            int counter3=0;
                            System.out.println("Introdu IBAN-ul contului vizat:");
                            String iban=sc.nextLine();
                            System.out.println("Introdu cnp-ul persoanei vizate:");
                            String cnpPersoana=sc.nextLine();

                            for(ContBancar cont: listaConturi){
                                if(cont.getNumarCont().equals(iban)){
                                    for(Person person:listaPersoane){
                                        if(person.getCNP().equals(cnpPersoana)){
                                            //PANA AICI IDENTIFICA IBAN DIN LISTA CONTURI SI PERSOANA VIZATA DIN LISTA PERSOANE

                                            if(totalConturiPersoane.get(person) == null) {   //DACA PERSOANA NU ARE O LISTA DE CONTURI ASOCIATA, SE CREEAZA UNA
                                                totalConturiPersoane.put(person, new ArrayList<>()); // SE ASOCIAZA PERSOANEI SI I SE ASOCIAZA SI CONTUL VIZAT
                                                for (Person person1 : totalConturiPersoane.keySet()) {
                                                    if (!totalConturiPersoane.get(person1).contains(cont)) {
                                                        totalConturiPersoane.get(person1).add(cont);
                                                    }
                                                }
                                            }else{
                                                //DACA PERSOANA ARE DEJA O LISTA ASOCIATA, SE ADAUGA DOAR CONTUL VIZAT
                                                for (Person person2 : totalConturiPersoane.keySet()) {
                                                    if (!totalConturiPersoane.get(person2).contains(cont)) {
                                                        totalConturiPersoane.get(person2).add(cont);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            break;

                        case 6:
                            for(Person person: totalConturiPersoane.keySet()){
                                System.out.println(person.getNume()+" "+person.getPrenume()+" "+person.getCNP()+" are "+totalConturiPersoane.get(person));
                            }
                            break;

                        case 7:
                            System.out.println("Introdu iban-ul contului principal:");
                            String contPrincipal=sc.nextLine();
                            System.out.println("Introdu iban-ul contului destinatar:");
                            String contDestinatar=sc.nextLine();
                            System.out.println("Introdu suma dorita pentru transfer:");
                            int sumaTransfer=Integer.valueOf(sc.nextLine());

                            for(ContBancar cont:listaConturi){
                                if(cont.getNumarCont().equals(contPrincipal)){
                                    for(ContBancar cont1:listaConturi){
                                        if(cont1.getNumarCont().equals(contDestinatar)){
                                            cont.transfer(cont1,sumaTransfer);
                                        }
                                    }
                                }
                            }
                            break;

                        case 8:
                            System.out.println("Introdu IBAN-ul contului in care depui bani:");
                            String contVizat=sc.nextLine();
                            System.out.println("Introdu suma dorita pentru alimentare:");
                            int sumaAlimentare=Integer.valueOf(sc.nextLine());

                            for(ContBancar cont:listaConturi){
                                if(cont.getNumarCont().equals(contVizat)){
                                    cont.depunere(sumaAlimentare);
                                }
                            }
                            break;

                        case 9:
                            System.out.println("Introdu IBAN-ul contului din care retragi bani:");
                            contVizat=sc.nextLine();
                            System.out.println("Introdu suma dorita pentru retragere:");
                            int sumaretragere=Integer.valueOf(sc.nextLine());

                            for(ContBancar cont:listaConturi){
                                if(cont.getNumarCont().equals(contVizat)){
                                    cont.retragere(sumaretragere);
                                }
                            }
                            break;

                        case 10: validator=false;
                            break;
                    }
                }

            }else {
                while (validator) {
                    System.out.println("\n~~~USER PANEL~~~\n" +
                            "\nAlege una dintre optiunile de mai jos:\n" +
                            "1.Vezi datele personale\n" +
                            "2.Vezi toate conturile\n" +
                            "3.Transfera bani\n" +
                            "4.Depune bani\n" +
                            "5.Retrage bani\n" +
                            "6.Inchide program\n");


                    Scanner sc = new Scanner(System.in);
                    int optiune = Integer.valueOf(sc.nextLine());

                    switch (optiune) {
                        case 1:
                            System.out.println("Datele tale personale:");

                            for (Person person : listaPersoane) {
                                if (person.getCNP().equals(user)) {
                                    System.out.println(person + " parola: " + listaUseri.get(user));
                                    ;
                                }
                            }
                            break;

                        case 2:
                            System.out.println("Lista conturilor tale:");

                            for (Person person : totalConturiPersoane.keySet()) {
                                if (person.getCNP().equals(user)) {
                                    System.out.println(totalConturiPersoane.get(person));
                                }
                            }
                            break;

                        case 3:
                            System.out.println("Introdu IBAN-ul contului din care transferi banii:");
                            String iban1=sc.nextLine();
                            System.out.println("Introdu IBAN-ul vizat pentru transfer:");
                            String iban2=sc.nextLine();
                            System.out.println("Introdu suma dorita pentru transfer:");
                            int suma =Integer.valueOf(sc.nextLine());

                            for (Person person : totalConturiPersoane.keySet()) {
                                if (person.getCNP().equals(user)) {
                                    for(ContBancar cont1:totalConturiPersoane.get(person)){
                                        if(cont1.getNumarCont().equals(iban1)){
                                            for(ContBancar cont2 : listaConturi){
                                                if(cont2.getNumarCont().equals(iban2)){
                                                    cont1.transfer(cont2,suma);
                                                }
                                            }
                                        }

                                    }

                                }
                            }

                            break;

                        case 4:
                            System.out.println("Introdu IBAN-ul contului in care depui banii:");
                            String iban=sc.nextLine();
                            System.out.println("Introdu suma dorita pentru depunere:");
                            suma =Integer.valueOf(sc.nextLine());

                            for (Person person : totalConturiPersoane.keySet()) {
                                if (person.getCNP().equals(user)) {
                                    for(ContBancar cont1:totalConturiPersoane.get(person)){
                                        if(cont1.getNumarCont().equals(iban)){
                                            cont1.depunere(suma);
                                        }
                                    }
                                }
                            }
                            break;

                        case 5:
                            System.out.println("Introdu IBAN-ul contului din care retragi banii:");
                            iban=sc.nextLine();
                            System.out.println("Introdu suma dorita pentru retragere:");
                            suma =Integer.valueOf(sc.nextLine());

                            for (Person person : totalConturiPersoane.keySet()) {
                                if (person.getCNP().equals(user)) {
                                    for(ContBancar cont1:totalConturiPersoane.get(person)){
                                        if(cont1.getNumarCont().equals(iban)){
                                            cont1.retragere(suma);
                                        }

                                    }

                                }
                            }
                            break;

                        case 6:
                            // Close program
                            validator = false;
                            break;
                    }

                }
            }
        }
    }
}
