package nationalid;
import java.util.Random;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

enum Gender {MALE, FEMALE}

enum Religion {
    ISLAM,
    CHRISTIANITY,
    JUDAISM
}

enum Governate {
    ALEXANDRIA, ASWAN, ASYUT, BEHEIRA, BENISUEF, CAIRO,
    DAKAHLIA, DAMIETTA, FAIYUM, GHARBIA, GIZA, ISMAILIA,
    KAFRELSHEIKH, LUXOR, MATROUH, MINYA, MENOFIA, NEWVALLEY,
    NORTHSINAI, PORTSAID, QALYUBIA, QENA, REDSEA, SHARQIA,
    SOHAG, SOUTHSINAI, SUEZ
}

enum Socialstatus {
    SINGLE, MARRIED, DIVORCED, SEPARATED, WINDOWED
}

public class NationalID {
    Random rand = new Random();
    //Attributes of the class
    private String name;
    private Gender gender;
    private Religion religion;
    private Governate governate;
    private String birthdate;
    private String id;
    private String dateofcreation;
    private String expirationdate;
    private String lastedit;
    private Socialstatus socialstatus;
    private String address;

    //Constructor
    public NationalID(String n, Governate g, String b, Gender g1, Socialstatus s, String a, Religion r) {
        name = n;
        governate = g;
        birthdate = b;
        gender = g1;
        LocalDate today = LocalDate.now();
        dateofcreation = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        lastedit = dateofcreation;
        expirationdate = expire();
        socialstatus = s;
        address = a;
        religion = r;
        generateID();
        showId();
    }

    public String expire() {
        String newyear = dateofcreation.substring(6);
        int year = Integer.parseInt(newyear);
        year += 7;
        newyear = String.valueOf(year);
        return dateofcreation.substring(0, 6) + newyear;
    }

    //setters
    public void setGender(Gender g) {
        gender = g;
        LocalDate today = LocalDate.now();
        lastedit = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void setReligion(Religion r) {
        religion = r;
        LocalDate today = LocalDate.now();
        lastedit = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void setName(String n) {
        name = n;
        LocalDate today = LocalDate.now();
        lastedit = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void setGovernate(Governate g) {
        governate = g;
        LocalDate today = LocalDate.now();
        lastedit = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void setBirthDate(String b) {
        birthdate = b;
        LocalDate today = LocalDate.now();
        lastedit = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    //Getters
    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public String getBirthDate() {
        return birthdate;
    }

    public Religion getReligion() {
        return religion;
    }

    public Governate getGovernate() {
        return governate;
    }

    public String getID() {
        return id;
    }

    public String getdateofcreation() {
        return dateofcreation;
    }

    public String getexpiration() {
        return expirationdate;
    }

    public String getAddress() {
        return address;
    }

    public Socialstatus getSocialstatus() {
        return socialstatus;
    }

    //Generating unique ID
    public void generateID() {
        String firstone = getfirstone();
        String from2to7 = gettwoseven();
        String from8to9 = eighttonine();
        String from10to13 = tentothirteen();
        String the14 = fourteenth();
        id = firstone + from2to7 + from8to9 + from10to13 + the14;
    }

    //Generating the part of year
    public String getfirstone() {
        String year = birthdate.substring(6);
        int yearint = Integer.parseInt(year);
        if (yearint >= 2000) {
            return "3";
        } else {
            return "2";
        }
    }

    //Generating the part of birth date
    public String gettwoseven() {
        String first2 = birthdate.substring(8);
        String second2 = birthdate.substring(3, 5);
        String third2 = birthdate.substring(0, 2);
        return first2 + second2 + third2;
    }

    //Generating the part of governate code
    public String eighttonine() {
        String str = "00";
        switch (governate) {
            case CAIRO: {
                str = "01";
                break;
            }
            case ALEXANDRIA: {
                str = "02";
                break;
            }
            case PORTSAID: {
                str = "03";
                break;
            }
            case SUEZ: {
                str = "04";
                break;
            }
            case DAMIETTA: {
                str = "11";
                break;
            }
            case DAKAHLIA: {
                str = "12";
                break;
            }
            case SHARQIA: {
                str = "13";
                break;
            }
            case QALYUBIA: {
                str = "14";
                break;
            }
            case KAFRELSHEIKH: {
                str = "15";
                break;
            }
            case GHARBIA: {
                str = "16";
                break;
            }
            case MENOFIA: {
                str = "17";
                break;
            }
            case ISMAILIA: {
                str = "19";
                break;
            }
            case GIZA: {
                str = "21";
                break;
            }
            case BENISUEF: {
                str = "22";
                break;
            }
            case FAIYUM: {
                str = "23";
                break;
            }
            case MINYA: {
                str = "24";
                break;
            }
            case ASYUT: {
                str = "25";
                break;
            }
            case SOHAG: {
                str = "26";
                break;
            }
            case QENA: {
                str = "27";
                break;
            }
            case ASWAN: {
                str = "28";
                break;
            }
            case LUXOR: {
                str = "29";
                break;
            }
            case REDSEA: {
                str = "31";
                break;
            }
            case NEWVALLEY: {
                str = "32";
                break;
            }
            case MATROUH: {
                str = "33";
                break;
            }
            case NORTHSINAI: {
                str = "34";
                break;
            }
            case SOUTHSINAI: {
                str = "35";
                break;
            }
        }
        return str;
    }

    //Generating the part of the order of birth
    public String tentothirteen() {
        String str = String.format("%03d", rand.nextInt(999));
        String str1 = "";
        boolean flag = true;
        while (flag) {
            int num = rand.nextInt(10);
            if (gender == Gender.MALE) {
                if (num % 2 != 0) {
                    str1 = String.format("%01d", num);
                    flag = false;
                }
            }
            if (gender == Gender.FEMALE) {
                if (num % 2 == 0) {
                    str1 = String.format("%01d", num);
                    flag = false;
                }
            }
        }
        return str + str1;
    }

    //Generating the number that tells the police whether the ID is real or fake
    public String fourteenth() {
        int num1 = rand.nextInt(10);
        String str = String.valueOf(num1);
        return str;
    }

    public void showId() {
        String divider = "═══════════════════════════════════════════";
        String separator = "───────────────────────────────────────────";

        System.out.println("\n" + divider);
        System.out.println("             PERSONAL IDENTIFICATION");
        System.out.println(divider + "\n");

        // Personal Information Section
        System.out.println("Personal Information");
        System.out.println(separator);
        printField("Name", name);
        printEnumField("Gender", gender);
        printEnumField("Religion", religion);
        printEnumField("Social Status", socialstatus);
        System.out.println();

        // Location Information
        System.out.println("Location Details");
        System.out.println(separator);
        printEnumField("Governate", governate);
        printField("Address", address);
        System.out.println();

        // Document Information
        System.out.println("Document Details");
        System.out.println(separator);
        printField("ID Number", id);
        printField("Date of Birth", birthdate);
        printField("Date of Creation", dateofcreation);
        printField("Expiration Date", expirationdate);
        printField("Last Modified", lastedit);

        System.out.println(divider + "\n");
    }

    private void printField(String label, String value) {
        String formattedLabel = String.format("%-15s", label);
        System.out.printf("%s : %s%n", formattedLabel, value);
    }

    private <T extends Enum<T>> void printEnumField(String label, T enumValue) {
        String formattedLabel = String.format("%-15s", label);
        String displayValue = formatEnumValue(enumValue);
        System.out.printf("%s : %s%n", formattedLabel, displayValue);
    }

    private String formatEnumValue(Enum<?> enumValue) {
        if (enumValue == null) {
            return "N/A";
        }
        String enumName = enumValue.name();
        String[] words = enumName.toLowerCase().split("_");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > 0) {
                result.append(words[i].substring(0, 1).toUpperCase())
                        .append(words[i].substring(1));
                if (i < words.length - 1) {
                    result.append(" ");
                }
            }
        }
        return result.toString();
    }
}