package ro.scoalainformala;


import java.util.*;

public class Main {

    public static void main(String[] args) {

        ArrayList<Person> listaPersoane = new ArrayList<>();
        ArrayList<ContBancar> listaConturi = new ArrayList<>();
        HashMap<Person,ArrayList> totalConturiPersoane = new HashMap<>();


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
                            listaPersoane.add(new Person(nume,prenume,cnp));
                            listaUseri.put(cnp,parola1);    //ADAUGA CNP SI PAROLA IN SECVENTA DE LOGIN
                            System.out.println("Ai inregistrat urmatoarea persoana: "+nume+" "+prenume+" (CNP: "+cnp+")");
                            break;
                        case 2: int counter=1;
                            if(!listaPersoane.isEmpty()){
                                for(Person x:listaPersoane){
                                    System.out.println(counter+". "+x);
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
                        case 4: int counter2=1;
                            if(!listaConturi.isEmpty()){
                                for(ContBancar y:listaConturi){
                                    System.out.println(counter2+". "+y);
                                }
                            }else {
                                System.out.println("Nu este inregistrat nici un cont!");
                            }
                            break;
                        case 5:
                            int counter3=0;
                            System.out.println("Introdu IBAN-ul contului vizat:");
                            String ibanVizat=sc.nextLine();
                            System.out.println("Introdu cnp-ul persoanei vizate:");
                            String cnpVizat=sc.nextLine();
                            for(ContBancar z: listaConturi){
                                if(z.getNumarCont().equals(ibanVizat)){
                                    for(Person f:listaPersoane){
                                        if(f.getCNP().equals(cnpVizat)){
                                            //PANA AICI IDENTIFICA IBAN SI PERSOANA VIZATA

                                            if(totalConturiPersoane.get(f) == null) {          //DACA PERSOANA NU ARE O LISTA DE CONTURI ASOCIATA, SE CREEAZA UNA
                                                totalConturiPersoane.put(f, new ArrayList<>()); // SE ASOCIAZA PERSOANEI SI I SE ASOCIAZA SI CONTUL VIZAT
                                                for (Person w : totalConturiPersoane.keySet()) {
                                                    if (!totalConturiPersoane.get(w).contains(z)) {
                                                        totalConturiPersoane.get(w).add(z);
                                                    }
                                                }
                                            }else{
                                                for (Person w : totalConturiPersoane.keySet()) { //DACA PERSOANA ARE DEJA O LISTA ASOCIATA, SE ADAUGA DOAR CONTUL VIZAT
                                                    if (!totalConturiPersoane.get(w).contains(z)) {
                                                        totalConturiPersoane.get(w).add(z);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 6: for(Person xx: totalConturiPersoane.keySet()){
                            System.out.println(xx.getNume()+" "+xx.getPrenume()+" "+xx.getCNP()+" are "+totalConturiPersoane.get(xx));
                        }
                            break;
                        case 7:
                            System.out.println("Introdu iban-ul contului principal:");
                            String contDonator=sc.nextLine();
                            System.out.println("Introdu iban-ul contului destinatar:");
                            String contDestinatar=sc.nextLine();
                            System.out.println("Introdu suma dorita pentru transfer:");
                            int sumaTransfer=Integer.valueOf(sc.nextLine());
                            for(ContBancar xxx:listaConturi){
                                if(xxx.getNumarCont().equals(contDonator)){
                                    for(ContBancar yyy:listaConturi){
                                        if(yyy.getNumarCont().equals(contDestinatar)){
                                            xxx.transfer(yyy,sumaTransfer);
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
                            for(ContBancar xxx:listaConturi){
                                if(xxx.getNumarCont().equals(contVizat)){
                                    xxx.depunere(sumaAlimentare);
                                }
                            }
                            break;
                        case 9: System.out.println("Introdu IBAN-ul contului din care retragi bani:");
                            String contVizat1=sc.nextLine();
                            System.out.println("Introdu suma dorita pentru retragere:");
                            int sumaretragere=Integer.valueOf(sc.nextLine());
                            for(ContBancar xxx:listaConturi){
                                if(xxx.getNumarCont().equals(contVizat1)){
                                    xxx.retragere(sumaretragere);
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
                            // View all accounts associated with the user
                            System.out.println("Lista conturilor tale:");
                            for (Person person : totalConturiPersoane.keySet()) {
                                if (person.getCNP().equals(user)) {
                                    System.out.println(totalConturiPersoane.get(person));
                                }
                            }
                            break;
                        case 3:

                            break;
                        case 4:

                            break;
                        case 5:

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
