package edu.buffalo.cse.irf14.utility;

import java.util.HashMap;
import java.util.Map;

public class FilterDatabase {
	
	public static final Map<String, String> accentMap = new HashMap<String, String>();
	public static final Map<String,String> contractionsMap = new HashMap<String,String>();
	
	static {
		
		accentMap.put("¿", "?");
		accentMap.put("À", "A");
		accentMap.put("Á", "A");
		accentMap.put("Â", "A");
		accentMap.put("Ã", "A");
		accentMap.put("Ä", "A");
		accentMap.put("Å", "A");
		accentMap.put("Æ", "AE");
		accentMap.put("Ç", "C");
		accentMap.put("È", "E");
		accentMap.put("É", "E");
		accentMap.put("Ê", "E");
		accentMap.put("Ë", "E");
		accentMap.put("Ì", "I");
		accentMap.put("Í", "I");
		accentMap.put("Î", "I");
		accentMap.put("Ï", "I");
		accentMap.put("Ð", "D");
		accentMap.put("Ñ", "N");
		accentMap.put("Ò", "O");
		accentMap.put("Ó", "O");
		accentMap.put("Ô", "O");
		accentMap.put("Õ", "O");
		accentMap.put("Ö", "O");
		accentMap.put("×", "x");
		accentMap.put("Ø", "O");
		accentMap.put("Ù", "U");
		accentMap.put("Ú", "U");
		accentMap.put("Û", "U");
		accentMap.put("Ü", "U");
		accentMap.put("Ý", "Y");
		accentMap.put("Þ", "P");
		accentMap.put("ß", "B");
		accentMap.put("à", "a");
		accentMap.put("á", "a");
		accentMap.put("â", "a");
		accentMap.put("ã", "a");
		accentMap.put("ä", "a");
		accentMap.put("å", "a");
		accentMap.put("æ", "ae");
		accentMap.put("ç", "c");
		accentMap.put("è", "e");
		accentMap.put("é", "e");
		accentMap.put("ê", "e");
		accentMap.put("ë", "e");
		accentMap.put("ì", "i");
		accentMap.put("í", "i");
		accentMap.put("î", "i");
		accentMap.put("ï", "i");
		accentMap.put("ð", "o");
		accentMap.put("ñ", "n");
		accentMap.put("ò", "o");
		accentMap.put("ó", "o");
		accentMap.put("ô", "o");
		accentMap.put("õ", "o");
		accentMap.put("ö", "o");
		accentMap.put("ø", "o");
		accentMap.put("ù", "u");
		accentMap.put("ú", "u");
		accentMap.put("û", "u");
		accentMap.put("ü", "u");
		accentMap.put("ý", "y");
		accentMap.put("þ", "p");
		accentMap.put("ÿ", "y");
		accentMap.put("€", "E");
		accentMap.put("ƒ", "f");
		accentMap.put("Š", "S");
		accentMap.put("Œ", "CE");
		accentMap.put("Ž", "Z");
		accentMap.put("š", "s");
		accentMap.put("œ", "ce");
		accentMap.put("ž", "z");
		accentMap.put("Ÿ", "Y");
		accentMap.put("Ā", "A");
		accentMap.put("ā", "a");
		accentMap.put("Ă", "A");
		accentMap.put("ă", "a");
		accentMap.put("Ą", "A");
		accentMap.put("ą", "a");
		accentMap.put("Ć", "C");
		accentMap.put("ć", "c");
		accentMap.put("Ĉ", "C");
		accentMap.put("ĉ", "c");
		accentMap.put("Ċ", "C");
		accentMap.put("ċ", "c");
		accentMap.put("Č", "C");
		accentMap.put("č", "c");
		accentMap.put("Ď", "D");
		accentMap.put("ď", "d");
		accentMap.put("Đ", "D");
		accentMap.put("đ", "d");
		accentMap.put("Ē", "E");
		accentMap.put("ē", "e");
		accentMap.put("Ĕ", "E");
		accentMap.put("ĕ", "e");
		accentMap.put("Ė", "E");
		accentMap.put("ė", "e");
		accentMap.put("Ę", "E");
		accentMap.put("ę", "e");
		accentMap.put("Ě", "E");
		accentMap.put("ě", "e");
		accentMap.put("Ĝ", "G");
		accentMap.put("ĝ", "g");
		accentMap.put("Ğ", "G");
		accentMap.put("ğ", "g");
		accentMap.put("Ġ", "G");
		accentMap.put("ġ", "g");
		accentMap.put("Ģ", "G");
		accentMap.put("ģ", "g");
		accentMap.put("Ĥ", "H");
		accentMap.put("ĥ", "h");
		accentMap.put("Ħ", "H");
		accentMap.put("ħ", "h");
		accentMap.put("Ĩ", "I");
		accentMap.put("ĩ", "i");
		accentMap.put("Ī", "I");
		accentMap.put("ī", "i");
		accentMap.put("Ĭ", "I");
		accentMap.put("ĭ", "i");
		accentMap.put("Į", "I");
		accentMap.put("į", "i");
		accentMap.put("İ", "I");
		accentMap.put("ı", "i");
		accentMap.put("Ĳ", "IJ");
		accentMap.put("ĳ", "ij");
		accentMap.put("Ĵ", "j");
		accentMap.put("ĵ", "j");
		accentMap.put("Ķ", "K");
		accentMap.put("ķ", "k");
		accentMap.put("ĸ", "K");
		accentMap.put("Ĺ", "L");
		accentMap.put("ĺ", "l");
		accentMap.put("Ļ", "L");
		accentMap.put("ļ", "l");
		accentMap.put("Ľ", "L");
		accentMap.put("ľ", "l");
		accentMap.put("Ŀ", "L");
		accentMap.put("ŀ", "l");
		accentMap.put("Ł", "L");
		accentMap.put("ł", "l");
		accentMap.put("Ń", "N");
		accentMap.put("ń", "n");
		accentMap.put("Ņ", "N");
		accentMap.put("ņ", "n");
		accentMap.put("Ň", "N");
		accentMap.put("ň", "n");
		accentMap.put("ŉ", "n");
		accentMap.put("Ŋ", "N");
		accentMap.put("ŋ", "n");
		accentMap.put("Ō", "O");
		accentMap.put("ō", "o");
		accentMap.put("Ŏ", "O");
		accentMap.put("ŏ", "o");
		accentMap.put("Ő", "O");
		accentMap.put("ő", "o");
		accentMap.put("Œ", "CE");
		accentMap.put("œ", "ce");
		accentMap.put("Ŕ", "R");
		accentMap.put("ŕ", "r");
		accentMap.put("Ŗ", "R");
		accentMap.put("ŗ", "r");
		accentMap.put("Ř", "R");
		accentMap.put("ř", "r");
		accentMap.put("Ś", "S");
		accentMap.put("ś", "s");
		accentMap.put("Ŝ", "S");
		accentMap.put("ŝ", "s");
		accentMap.put("Ş", "S");
		accentMap.put("ş", "s");
		accentMap.put("Š", "S");
		accentMap.put("š", "s");
		accentMap.put("Ţ", "T");
		accentMap.put("ţ", "t");
		accentMap.put("Ť", "T");
		accentMap.put("ť", "t");
		accentMap.put("Ŧ", "T");
		accentMap.put("ŧ", "t");
		accentMap.put("Ũ", "U");
		accentMap.put("ũ", "u");
		accentMap.put("Ū", "U");
		accentMap.put("ū", "u");
		accentMap.put("Ŭ", "U");
		accentMap.put("ŭ", "u");
		accentMap.put("Ů", "U");
		accentMap.put("ů", "u");
		accentMap.put("Ű", "U");
		accentMap.put("ű", "u");
		accentMap.put("Ų", "U");
		accentMap.put("ų", "u");
		accentMap.put("Ŵ", "W");
		accentMap.put("ŵ", "w");
		accentMap.put("Ŷ", "Y");
		accentMap.put("ŷ", "y");
		accentMap.put("Ÿ", "Y");
		accentMap.put("Ź", "Z");
		accentMap.put("ź", "z");
		accentMap.put("Ż", "Z");
		accentMap.put("ż", "z");
		accentMap.put("Ž", "Z");
		accentMap.put("ž", "z");
		accentMap.put("ſ", "r");
		accentMap.put("ƀ", "b");
		accentMap.put("Ɓ", "B");
		accentMap.put("Ƃ", "b");
		accentMap.put("ƃ", "b");
		accentMap.put("Ƅ", "b");
		accentMap.put("ƅ", "b");
		accentMap.put("Ɔ", "J");
		accentMap.put("Ƈ", "C");
		accentMap.put("ƈ", "c");
		accentMap.put("Ɖ", "D");
		accentMap.put("Ɗ", "D");
		accentMap.put("Ƌ", "a");
		accentMap.put("ƌ", "a");
		accentMap.put("ƍ", "Q");
		accentMap.put("Ǝ", "E");
		accentMap.put("Ə", "e");
		accentMap.put("Ɛ", "E");
		accentMap.put("Ƒ", "F");
		accentMap.put("ƒ", "f");
		accentMap.put("Ɠ", "G");
		accentMap.put("Ɣ", "Y");
		accentMap.put("ƕ", "h");
		accentMap.put("Ɩ", "l");
		accentMap.put("Ɨ", "EE");
		accentMap.put("Ƙ", "K");
		accentMap.put("ƙ", "k");
		accentMap.put("Ɯ", "W");
		accentMap.put("Ɲ", "N");
		accentMap.put("ƞ", "n");
		accentMap.put("Ɵ", "O");
		accentMap.put("Ơ", "O");
		accentMap.put("ơ", "o");
		accentMap.put("Ƥ", "p");
		accentMap.put("ƥ", "p");
		accentMap.put("Ʀ", "R");
		accentMap.put("Ƨ", "S");
		accentMap.put("ƨ", "s");
		accentMap.put("Ʃ", "E");
		accentMap.put("ƪ", "q");
		accentMap.put("ƫ", "t");
		accentMap.put("Ƭ", "T");
		accentMap.put("ƭ", "t");
		accentMap.put("Ʈ", "T");
		accentMap.put("Ư", "U");
		accentMap.put("ư", "u");
		accentMap.put("Ʊ", "U");
		accentMap.put("Ʋ", "U");
		accentMap.put("Ƴ", "Y");
		accentMap.put("ƴ", "y");
		accentMap.put("Ƶ", "Z");
		accentMap.put("ƶ", "z");
		accentMap.put("Ʒ", "z");
		accentMap.put("Ƹ", "z");
		accentMap.put("ƹ", "z");
		accentMap.put("ƺ", "z");
		accentMap.put("ƿ", "p");
		accentMap.put("ǈ", "lj");
		accentMap.put("ǉ", "lj");
		accentMap.put("ǋ", "Nj");
		accentMap.put("ǌ", "nj");
		accentMap.put("Ǎ", "A");
		accentMap.put("ǎ", "a");
		accentMap.put("Ǐ", "I");
		accentMap.put("ǐ", "i");
		accentMap.put("Ǒ", "O");
		accentMap.put("ǒ", "o");
		accentMap.put("Ǔ", "U");
		accentMap.put("ǔ", "u");
		accentMap.put("Ǖ", "U");
		accentMap.put("ǖ", "u");
		accentMap.put("Ǘ", "U");
		accentMap.put("ǘ", "u");
		accentMap.put("Ǚ", "U");
		accentMap.put("ǚ", "u");
		accentMap.put("Ǜ", "U");
		accentMap.put("ǜ", "u");
		accentMap.put("ǝ", "e");
		accentMap.put("Ǟ", "A");
		accentMap.put("ǟ", "a");
		accentMap.put("Ǡ", "A");
		accentMap.put("ǡ", "a");
		accentMap.put("Ǣ", "AE");
		accentMap.put("ǣ", "ae");
		accentMap.put("Ǥ", "G");
		accentMap.put("ǥ", "g");
		accentMap.put("Ǧ", "G");
		accentMap.put("ǧ", "g");
		accentMap.put("Ǩ", "K");
		accentMap.put("ǩ", "k");
		accentMap.put("Ǫ", "Q");
		accentMap.put("ǫ", "q");
		accentMap.put("Ǭ", "Q");
		accentMap.put("ǭ", "q");
		accentMap.put("Ǯ", "z");
		accentMap.put("ǯ", "z");
		accentMap.put("ǰ", "j");
		accentMap.put("Ǳ", "DZ");
		accentMap.put("ǲ", "Dz");
		accentMap.put("ǳ", "dz");
		accentMap.put("Ǵ", "G");
		accentMap.put("ǵ", "g");
		accentMap.put("Ƕ", "H");
		accentMap.put("Ƿ", "P");
		accentMap.put("Ǹ", "N");
		accentMap.put("ǹ", "n");
		accentMap.put("Ǻ", "A");
		accentMap.put("ǻ", "a");
		accentMap.put("Ǽ", "AE");
		accentMap.put("ǽ", "ae");
		accentMap.put("Ǿ", "O");
		accentMap.put("ǿ", "o");
		accentMap.put("Ȁ", "A");
		accentMap.put("ȁ", "a");
		accentMap.put("Ȃ", "A");
		accentMap.put("ȃ", "a");
		accentMap.put("Ȅ", "E");
		accentMap.put("ȅ", "e");
		accentMap.put("Ȇ", "E");
		accentMap.put("ȇ", "e");
		accentMap.put("Ȉ", "I");
		accentMap.put("ȉ", "i");
		accentMap.put("Ȋ", "I");
		accentMap.put("ȋ", "i");
		accentMap.put("Ȍ", "O");
		accentMap.put("ȍ", "o");
		accentMap.put("Ȏ", "O");
		accentMap.put("ȏ", "o");
		accentMap.put("Ȑ", "R");
		accentMap.put("ȑ", "r");
		accentMap.put("Ȓ", "R");
		accentMap.put("ȓ", "r");
		accentMap.put("Ȕ", "U");
		accentMap.put("ȕ", "u");
		accentMap.put("Ȗ", "U");
		accentMap.put("ȗ", "u");
		accentMap.put("Ș", "S");
		accentMap.put("ș", "s");
		accentMap.put("Ț", "T");
		accentMap.put("ț", "t");
		accentMap.put("Ȝ", "z");
		accentMap.put("ȝ", "z");
		accentMap.put("Ȟ", "H");
		accentMap.put("ȟ", "h");
		accentMap.put("Ƞ", "n");
		accentMap.put("Ȥ", "Z");
		accentMap.put("ȥ", "z");
		accentMap.put("Ȧ", "A");
		accentMap.put("ȧ", "a");
		accentMap.put("Ȩ", "E");
		accentMap.put("ȩ", "e");
		accentMap.put("Ȫ", "O");
		accentMap.put("ȫ", "o");
		accentMap.put("Ȭ", "O");
		accentMap.put("ȭ", "o");
		accentMap.put("Ȯ", "O");
		accentMap.put("ȯ", "o");
		accentMap.put("Ȱ", "O");
		accentMap.put("ȱ", "o");
		accentMap.put("Ȳ", "Y");
		accentMap.put("ȳ", "y");
		accentMap.put("ȴ", "b");
		accentMap.put("ȵ", "n");
		accentMap.put("ȶ", "t");
		accentMap.put("ȷ", "J");
		accentMap.put("Ⱥ", "A");
		accentMap.put("Ȼ", "C");
		accentMap.put("ȼ", "c");
		accentMap.put("Ƚ", "t");
		accentMap.put("Ⱦ", "T");
		accentMap.put("Ƀ", "B");
		accentMap.put("Ʉ", "U");
		accentMap.put("Ʌ", "A");
		accentMap.put("Ɇ", "E");
		accentMap.put("ɇ", "e");
		accentMap.put("Ɉ", "J");
		accentMap.put("ɉ", "j");
		accentMap.put("Ɋ", "Q");
		accentMap.put("ɋ", "q");
		accentMap.put("Ɍ", "R");
		accentMap.put("ɍ", "r");
		accentMap.put("Ɏ", "Y");
		accentMap.put("ɏ", "y");
		accentMap.put("Ѐ", "E");
		accentMap.put("Ё", "E");
		accentMap.put("Ђ", "h");
		accentMap.put("Ѓ", "r");
		accentMap.put("Є", "E");
		accentMap.put("Ѕ", "S");
		accentMap.put("І", "I");
		accentMap.put("Ї", "I");
		accentMap.put("Ј", "J");
		accentMap.put("Љ", "b");
		accentMap.put("Њ", "b");
		accentMap.put("Ћ", "h");
		accentMap.put("Ќ", "K");
		accentMap.put("Ѝ", "N");
		accentMap.put("Ў", "y");
		accentMap.put("Џ", "U");
		accentMap.put("А", "A");
		accentMap.put("Б", "b");
		accentMap.put("В", "B");
		accentMap.put("Г", "r");
		accentMap.put("Д", "A");
		accentMap.put("Е", "E");
		accentMap.put("Ж", "X");
		accentMap.put("З", "E");
		accentMap.put("И", "N");
		accentMap.put("Й", "N");
		accentMap.put("К", "K");
		accentMap.put("Л", "n");
		accentMap.put("М", "M");
		accentMap.put("Н", "H");
		accentMap.put("О", "O");
		accentMap.put("П", "n");
		accentMap.put("Р", "p");
		accentMap.put("С", "C");
		accentMap.put("Т", "T");
		accentMap.put("У", "y");
		accentMap.put("Ф", "O");
		accentMap.put("Х", "X");
		accentMap.put("Ц", "U");
		accentMap.put("Ч", "U");
		accentMap.put("Ш", "W");
		accentMap.put("Щ", "W");
		accentMap.put("Ъ", "b");
		accentMap.put("Ы", "bl");
		accentMap.put("Ь", "b");
		accentMap.put("Э", "E");
		accentMap.put("Я", "R");
		accentMap.put("а", "a");
		accentMap.put("б", "6");
		accentMap.put("в", "B");
		accentMap.put("г", "r");
		accentMap.put("д", "A");
		accentMap.put("е", "e");
		accentMap.put("ж", "x");
		accentMap.put("з", "z");
		accentMap.put("и", "N");
		accentMap.put("й", "N");
		accentMap.put("к", "K");
		accentMap.put("л", "n");
		accentMap.put("м", "M");
		accentMap.put("н", "H");
		accentMap.put("о", "o");
		accentMap.put("п", "n");
		accentMap.put("р", "p");
		accentMap.put("с", "c");
		accentMap.put("т", "T");
		accentMap.put("у", "y");
		accentMap.put("ф", "o");
		accentMap.put("х", "x");
		accentMap.put("ц", "u");
		accentMap.put("ч", "u");
		accentMap.put("ш", "w");
		accentMap.put("щ", "w");
		accentMap.put("ъ", "b");
		accentMap.put("ы", "bl");
		accentMap.put("ь", "b");
		accentMap.put("э", "E");
		accentMap.put("я", "R");
		accentMap.put("ѐ", "e");
		accentMap.put("ё", "e");
		accentMap.put("ђ", "h");
		accentMap.put("ѓ", "r");
		accentMap.put("є", "E");
		accentMap.put("ѕ", "s");
		accentMap.put("і", "i");
		accentMap.put("ї", "i");
		accentMap.put("ј", "j");
		accentMap.put("ћ", "h");
		accentMap.put("ќ", "K");
		accentMap.put("ѝ", "N");
		accentMap.put("ў", "y");
		accentMap.put("џ", "u");
		accentMap.put("Ѡ", "W");
		accentMap.put("ѡ", "w");
		accentMap.put("Ѣ", "b");
		accentMap.put("ѣ", "b");
		accentMap.put("Ѥ", "H");
		accentMap.put("ѥ", "h");
		accentMap.put("Ѧ", "A");
		accentMap.put("ѧ", "a");
		accentMap.put("Ѱ", "W");
		accentMap.put("ѱ", "w");
		accentMap.put("Ѳ", "O");
		accentMap.put("ѳ", "o");
		accentMap.put("Ѵ", "V");
		accentMap.put("ѵ", "v");
		accentMap.put("Ѷ", "V");
		accentMap.put("ѷ", "v");
		accentMap.put("Ѻ", "O");
		accentMap.put("Ҋ", "N");
		accentMap.put("ҋ", "N");
		accentMap.put("Ҍ", "b");
		accentMap.put("ҍ", "b");
		accentMap.put("Ҏ", "R");
		accentMap.put("ҏ", "R");
		accentMap.put("Ґ", "r");
		accentMap.put("ґ", "r");
		accentMap.put("Ғ", "f");
		accentMap.put("ғ", "f");
		accentMap.put("Ҕ", "h");
		accentMap.put("ҕ", "h");
		accentMap.put("Җ", "X");
		accentMap.put("җ", "x");
		accentMap.put("Ҙ", "z");
		accentMap.put("ҙ", "z");
		accentMap.put("Қ", "K");
		accentMap.put("қ", "K");
		accentMap.put("Ҝ", "K");
		accentMap.put("ҝ", "K");
		accentMap.put("Ҟ", "K");
		accentMap.put("ҟ", "k");
		accentMap.put("Ҡ", "K");
		accentMap.put("ҡ", "k");
		accentMap.put("Ң", "H");
		accentMap.put("ң", "H");
		accentMap.put("Ҥ", "H");
		accentMap.put("ҥ", "H");
		accentMap.put("Ҫ", "C");
		accentMap.put("ҫ", "c");
		accentMap.put("Ҭ", "T");
		accentMap.put("ҭ", "T");
		accentMap.put("Ү", "Y");
		accentMap.put("ү", "y");
		accentMap.put("Ұ", "Y");
		accentMap.put("ұ", "Y");
		accentMap.put("Ҳ", "X");
		accentMap.put("ҳ", "x");
		accentMap.put("Ҵ", "U");
		accentMap.put("ҵ", "u");
		accentMap.put("Ҷ", "u");
		accentMap.put("ҷ", "u");
		accentMap.put("Ҹ", "u");
		accentMap.put("ҹ", "u");
		accentMap.put("Һ", "h");
		accentMap.put("һ", "h");
		accentMap.put("Ҽ", "e");
		accentMap.put("ҽ", "e");
		accentMap.put("Ҿ", "e");
		accentMap.put("ҿ", "e");
		accentMap.put("Ӏ", "I");
		accentMap.put("Ӂ", "X");
		accentMap.put("ӂ", "x");
		accentMap.put("Ӄ", "K");
		accentMap.put("ӄ", "K");
		accentMap.put("Ӆ", "n");
		accentMap.put("Ӈ", "H");
		accentMap.put("ӈ", "H");
		accentMap.put("Ӊ", "H");
		accentMap.put("ӊ", "H");
		accentMap.put("Ӌ", "u");
		accentMap.put("ӌ", "u");
		accentMap.put("Ӎ", "M");
		accentMap.put("ӎ", "m");
		accentMap.put("ӏ", "I");
		accentMap.put("Ӑ", "A");
		accentMap.put("ӑ", "a");
		accentMap.put("Ӓ", "A");
		accentMap.put("ӓ", "a");
		accentMap.put("Ӕ", "AE");
		accentMap.put("ӕ", "ae");
		accentMap.put("Ӗ", "E");
		accentMap.put("ӗ", "e");
		accentMap.put("Ә", "e");
		accentMap.put("ә", "e");
		accentMap.put("Ӛ", "e");
		accentMap.put("ӛ", "e");
		accentMap.put("Ӝ", "X");
		accentMap.put("ӝ", "x");
		accentMap.put("Ӟ", "z");
		accentMap.put("ӟ", "z");
		accentMap.put("Ӡ", "z");
		accentMap.put("ӡ", "z");
		accentMap.put("Ӣ", "N");
		accentMap.put("ӣ", "N");
		accentMap.put("Ӥ", "N");
		accentMap.put("ӥ", "N");
		accentMap.put("Ӧ", "O");
		accentMap.put("ӧ", "o");
		accentMap.put("Ө", "O");
		accentMap.put("ө", "o");
		accentMap.put("Ӫ", "o");
		accentMap.put("ӫ", "o");
		accentMap.put("Ӭ", "E");
		accentMap.put("ӭ", "e");
		accentMap.put("Ӯ", "y");
		accentMap.put("ӯ", "y");
		accentMap.put("Ӱ", "y");
		accentMap.put("ӱ", "y");
		accentMap.put("Ӳ", "y");
		accentMap.put("ӳ", "y");
		accentMap.put("Ӵ", "u");
		accentMap.put("ӵ", "u");
		accentMap.put("Ӷ", "r");
		accentMap.put("ӷ", "r");
		accentMap.put("Ӹ", "bl");
		accentMap.put("ӹ", "bl");
		accentMap.put("Ӻ", "f");
		accentMap.put("ӻ", "f");
		accentMap.put("Ӽ", "X");
		accentMap.put("ӽ", "x");
		accentMap.put("Ӿ", "x");
		accentMap.put("ӿ", "x");
		
		
		
		contractionsMap.put("can't","cannot");
		contractionsMap.put("could've","could have");
		contractionsMap.put("couldn't","could not");
		contractionsMap.put("couldn't've","could not have");
		contractionsMap.put("didn't","did not");
		contractionsMap.put("doesn't","does not");
		contractionsMap.put("don't","do not");
		contractionsMap.put("'em", "them");
		contractionsMap.put("hadn't","had not");
		contractionsMap.put("hadn't've","had not have");
		contractionsMap.put("hasn't","has not");
		contractionsMap.put("haven't","have not");
		contractionsMap.put("he'd","he had");
		contractionsMap.put("he'd've","he would have");
		contractionsMap.put("he'll","he shall");
		contractionsMap.put("he's","he has");
		contractionsMap.put("how'd","how did");
		contractionsMap.put("how'll","how will");
		contractionsMap.put("how's","how has");
		contractionsMap.put("i'd","I had");
		contractionsMap.put("i'd've","I would have");
		contractionsMap.put("i'll","I shall");
		contractionsMap.put("i'm","I am");
		contractionsMap.put("i've","I have");
		contractionsMap.put("isn't","is not");
		contractionsMap.put("it'd","it had");
		contractionsMap.put("it'd've","it would have");
		contractionsMap.put("it'll","it shall");
		contractionsMap.put("it's","it has");
		contractionsMap.put("let's","let us");
		contractionsMap.put("ma'am","madam");
		contractionsMap.put("mightn't","might not");
		contractionsMap.put("mightn't've","might not have");
		contractionsMap.put("might've","might have");
		contractionsMap.put("mustn't","must not");
		contractionsMap.put("must've","must have");
		contractionsMap.put("needn't","need not");
		contractionsMap.put("not've","not have");
		contractionsMap.put("o'clock","of the clock");
		contractionsMap.put("shan't","shall not");
		contractionsMap.put("she'd","she had");
		contractionsMap.put("she'd've","she would have");
		contractionsMap.put("she'll","she will");
		contractionsMap.put("she's","she has");
		contractionsMap.put("should've","should have");
		contractionsMap.put("shouldn't","should not");
		contractionsMap.put("shouldn't've","should not have");
		contractionsMap.put("that's","that has");
		contractionsMap.put("there'd","there had");
		contractionsMap.put("there'd've","there would have");
		contractionsMap.put("there're","there are");
		contractionsMap.put("there's","there has");
		contractionsMap.put("they'd","they would");
		contractionsMap.put("they'd've","they would have");
		contractionsMap.put("they'll","they shall");
		contractionsMap.put("they're","they are");
		contractionsMap.put("they've","they have");
		contractionsMap.put("wasn't","was not");
		contractionsMap.put("we'd","we had");
		contractionsMap.put("we'd've","we would have");
		contractionsMap.put("we'll","we will");
		contractionsMap.put("we're","we are");
		contractionsMap.put("we've","we have");
		contractionsMap.put("weren't","were not");
		contractionsMap.put("what'll","what shall");
		contractionsMap.put("what're","what are");
		contractionsMap.put("what's","what has");
		contractionsMap.put("what've","what have");
		contractionsMap.put("when's","when has");
		contractionsMap.put("where'd","where did");
		contractionsMap.put("where's","where has");
		contractionsMap.put("where've","where have");
		contractionsMap.put("who'd","who would");
		contractionsMap.put("who'll","who shall");
		contractionsMap.put("who're","who are");
		contractionsMap.put("who's","who has");
		contractionsMap.put("who've","who have");
		contractionsMap.put("why'll","why will");
		contractionsMap.put("why're","why are");
		contractionsMap.put("why's","why has");
		contractionsMap.put("won't","will not");
		contractionsMap.put("would've","would have");
		contractionsMap.put("wouldn't","would not");
		contractionsMap.put("wouldn't've","would not have");
		contractionsMap.put("y'all","you all");
		contractionsMap.put("y'all'd've","you all should have");
		contractionsMap.put("you'd","you had");
		contractionsMap.put("you'd've","you would have");
		contractionsMap.put("you'll","you shall");
		contractionsMap.put("you're","you are");
		contractionsMap.put("you've","you have");
	}
	
	public static final String[] stopWordList = {"a",
		"able",
		"about",
		"across",
		"after",
		"all",
		"almost",
		"also",
		"am",
		"among",
		"an",
		"and",
		"any",
		"are",
		"as",
		"at",
		"be",
		"because",
		"been",
		"but",
		"by",
		"can",
		"cannot",
		"could",
		"dear",
		"did",
		"do",
		"does",
		"either",
		"else",
		"ever",
		"every",
		"for",
		"from",
		"get",
		"got",
		"had",
		"has",
		"have",
		"he",
		"her",
		"hers",
		"him",
		"his",
		"how",
		"however",
		"i",
		"if",
		"in",
		"into",
		"is",
		"it",
		"its",
		"just",
		"least",
		"let",
		"like",
		"likely",
		"may",
		"me",
		"might",
		"most",
		"must",
		"my",
		"neither",
		"no",
		"nor",
		"not",
		"of",
		"off",
		"often",
		"on",
		"only",
		"or",
		"other",
		"our",
		"own",
		"rather",
		"said",
		"say",
		"says",
		"she",
		"should",
		"since",
		"so",
		"some",
		"than",
		"that",
		"the",
		"their",
		"them",
		"then",
		"there",
		"these",
		"they",
		"this",
		"tis",
		"to",
		"too",
		"twas",
		"us",
		"wants",
		"was",
		"we",
		"were",
		"what",
		"when",
		"where",
		"which",
		"while",
		"who",
		"whom",
		"why",
		"will",
		"with",
		"would",
		"yet",
		"you",
		"your"};

}
