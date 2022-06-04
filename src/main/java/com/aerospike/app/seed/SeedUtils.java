
package com.aerospike.app.seed;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * Helper class to generate data
 * @author Tim
 *
 */
public class SeedUtils {

	public static enum Alphabet {
		LOWER_LETTERS("abcdefghijklmnopqrstuvwxyz"),
		UPPER_LETTERS("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
		NUMBERS("0123456789"),
		ALPHABET(LOWER_LETTERS.getMembers() + UPPER_LETTERS.getMembers()),
		ALPHA_NUM_UPPER(UPPER_LETTERS.getMembers() + NUMBERS.getMembers()),
		ALPHA_NUM(ALPHABET.getMembers() + NUMBERS.getMembers());

		private final String members;
		private Alphabet(String s) {
			this.members = s;
		}

		public String getMembers() {
			return members;
		}
	};

	private static final String[] FIRST_NAMES = {
		"Albert",
		"Alfred",
		"Amelia",
		"Ava",
		"Benjamin",
		"Catherine",
		"Charlotte",
		"Cindy",
		"Elijah",
		"Emma",
		"Evelyn",
		"Harper",
		"James",
		"Jessica",
		"Liam",
		"Logan",
		"Lucas",
		"Isabella",
		"Mason",
		"Mia",
		"Michael",
		"Michelle",
		"Noah",
		"Oliver",
		"Olivia",
		"Rachel",
		"Sophia",
		"Stephen",
		"Valerie",
		"Wendy",
		"William",
		"Wilma",
		"Zeta"
	};

	private static final String[] LAST_NAMES = {
		"Beagley",
		"Brown",
		"Davis",
		"Devi",
		"Emery",
		"Fox",
		"Garcia",
		"Grossman",
		"Hague",
		"Johnson",
		"Jones",
		"Kim",
		"Kumar",
		"Li",
		"Liu",
		"Miller",
		"Park",
		"Queen",
		"Reacher",
		"Singh",
		"Smith",
		"Wang",
		"Williams",
		"Yang",
		"Zhang"
	};

	private static final String[] CITY_NAMES = {
		"London",
		"Detroit",
		"Maine",
		"Northampton",
		"Dover",
		"Peterborough",
		"Milton Keynes",
		"New York",
		"Los Angeles",
		"Chicago",
		"Houston",
		"Phoenix",
		"Philadelphia",
		"San Antonio",
		"San Diego",
		"Dallas",
		"San Jose",
		"Austin",
		"Fort Worth",
		"Jacksonville",
		"Columbus",
		"Charlotte",
		"San Francisco",
		"Indianapolis",
		"Seattle",
		"Denver",
		"Washington",
		"Boston",
		"El Paso",
		"Nashville",
		"Detroit"
	};

	private static final String[] CITY_ST_ZIP_NAMES = {
		"Aberdeen, SD 57401",
		"Altavista, VA 24517",
		"Anchorage, AK 99501",
		"Ashland, NH 03217",
		"Austin, TX 78701",
		"Baltimore, MD 21201",
		"Beaver, WV 25813",
		"Biloxi, MS 39530",
		"Boston, MA 02101",
		"Camden, SC 29020",
		"Cleveland, OH 44101",
		"Denver, CO 80201",
		"Dover, DE 19901",
		"Duluth, MN 55801",
		"Freeport, ME 04032",
		"Hartford, CT 06101",
		"Hastings, NE 68901",
		"Hazard, KY 41701",
		"Honolulu, HI 96801",
		"Huntsville, AL 35801",
		"Indianapolis, IN 46201",
		"Killington, VT 05751",
		"Laurel, MT 59044",
		"Little Rock, AR 72201",
		"Livingston, NJ 07039",
		"Logan, UT 84321",
		"Milwaukee, WI 53201",
		"Montpelier, ID 83254",
		"Nashville, TN 37201",
		"New Orleans, LA 70112",
		"New York, NY 10001",
		"Newport, RI 02840",
		"Oxford, NC 27565",
		"Phoenix, AZ 85001",
		"Pinedale, WY 82941",
		"Pittsburgh, PA 15201",
		"Portland, OR 97201",
		"Reno, NV 89501",
		"Santa Fe, NM 87500",
		"St. Louis, MO 63101",
		"Tulsa, OK 74101",
		"Walhalla, ND 58282",
		"Wichita, KS 67201"
	};

	public String getFirstName(Random r) {
		int index = r.nextInt(FIRST_NAMES.length);
		return FIRST_NAMES[index];
	}

	public String getLastName(Random r) {
		int index = r.nextInt(LAST_NAMES.length);
		return LAST_NAMES[index];
	}

	public String getAddress(Random r) {
		return r.nextInt(2000) + " " + getFirstName(r) + " St";
	}

	public String getCity(Random r) {
		return CITY_NAMES[r.nextInt(CITY_NAMES.length)];
	}

	public String getZip(Random r) {
		return getString(r, 5, Alphabet.NUMBERS);
	}

	public String getCityStZip(Random r) {
		return CITY_ST_ZIP_NAMES[r.nextInt(CITY_ST_ZIP_NAMES.length)];
	}

	public String getFullAddress(Random r) {
		return getAddress(r) + ", " + getCityStZip(r);
	}

	public String getName(Random r) {
		return getFirstName(r) + " " + getLastName(r);
	}

	public String getNumber(Random r, int limit, int padding) {
		return formatNumber(r.nextInt(limit), padding);
	}
	public String formatNumber(long n, int padding) {
		return formatNumber(n, padding, 1, 0);
	}

	public String formatNumber(long n, int padding, long multiplier, long offset) {
		long number = offset + n * multiplier;
		if (padding > 0) {
			String format = "%0" + padding + "d";
			return String.format(format, number);
		}
		else {
			return Long.toString(number);
		}
	}

	public short getShort(Random r) {
		return (short) r.nextInt();
	}

	public int getInt(Random r) {
		return r.nextInt();
	}

	public long getLong(Random r) {
		return r.nextInt();
	}

	public double getDouble(Random r) {
		return r.nextInt();
	}

	public char[] getCharArray(Random r, int length) {
		char[] charArray = new char[length];
		for (int i=0; i<length; i++) {
			charArray[i] = getString(r, 1, Alphabet.UPPER_LETTERS).charAt(0);
		}
		return charArray;
	}

	public byte getByte(Random r) {
		String str = getString(r, 1, Alphabet.ALPHA_NUM);
		return str.getBytes()[0];
	}

	public byte[] getBytes(Random r, int length) {
		String str = getString(r, length, Alphabet.ALPHA_NUM);
		return str.getBytes();
	}

	public char getChar(Random r) {
		String str = getString(r, 1, Alphabet.ALPHA_NUM);
		return str.toCharArray()[0];
	}

	public char[] getChars(Random r, int length) {
		String str = getString(r, length, Alphabet.ALPHA_NUM);
		return str.toCharArray();
	}

	public List<byte[]> getBytesArray(Random r, int length, int size) {
		List<byte[]> ba = new ArrayList<byte[]>();
		for (int i = 0; i < length; i++) {
			ba.add(getBytes(r, size));
		}
		return ba;
	}

	public double[] getDoubleArray(Random r, int length) {
		double[] da = new double[length];
		for (int i = 0; i < length; i++) {
			da[i] = getInt(r);
		}
		return da;
	}

	public int[] getIntArray(Random r, int length) {
		int[] ia = new int[length];
		for (int i = 0; i < length; i++) {
			ia[i] = getInt(r);
		}
		return ia;
	}

	public short[] getShortArray(Random r, int length) {
		short[] sa = new short[length];
		for (int i = 0; i < length; i++) {
			sa[i] = getShort(r);
		}
		return sa;
	}

	public List<Integer> getIntegerList(Random r, int length) {
		List<Integer> il = new ArrayList<Integer>(length);
		for (int i = 0; i < length; i++) {
			il.add(i, getInt(r));
		}
		return il;
	}

	public List<Short> getShortList(Random r, int length) {
		List<Short> sl = new ArrayList<Short>(length);
		for (int i = 0; i < length; i++) {
			sl.add(i, getShort(r));
		}
		return sl;
	}

	public List<Byte> getByteList(Random r, int length) {
		List<Byte> bl = new ArrayList<Byte>(length);
		for (int i = 0; i < length; i++) {
			bl.add(i, getByte(r));
		}
		return bl;
	}

	public List<String> getStringList(Random r, int length, int size) {
		List<String> ba = new ArrayList<String>();
		for (int i = 0; i < length; i++) {
			ba.add(getString(r, size));
		}
		return ba;
	}

	public String getString(Random r, int length) {
		return getString(r, length, Alphabet.UPPER_LETTERS);
	}

	public String getString(Random r, int length, Alphabet alphabet) {
		return getString(r, length, 0, alphabet);
	}

	public String getString(Random r, int length, int repetition, Alphabet alphabet) {
		StringBuilder sb = new StringBuilder(length);
		String s = alphabet.getMembers();
		int len = s.length();
		for (int i = 0; i < length; i++) {
			sb.append(s.charAt(r.nextInt(len)));
			for (int j = 0; j < repetition; i++, j++) {
				sb.append(s.charAt(0));
				if (i >= length) {
					break;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Distribute a set of values over the given percentages. The return value is an integer in the range
	 * 0 .. percentages.length()-1, which indicates which range the next random number falls into.
	 * <pre>
	 *		SeedUtils u = new SeedUtils();
	 *		int a = 0, b = 0, c= 0;
	 *		for (int i = 0; i < 10000; i++) {
	 *			switch(u.distribute(random, 80,15,5)) {
	 *			case 0: a++; break;
	 *			case 1: b++; break;
	 *			case 2: c++; break;
	 *			}
	 *		}
	 *		System.out.printf("a = %d, b = %d, c = %d\n", a, b, c);
	 * </pre>
	 *
	 * Should output something similar to:
	 * a = 8025, b = 1482, c = 493
	 *
	 * @param r
	 * @param percentages
	 * @return
	 */
	public int distribute(Random r, int ... percentages) {
		int sum = 0;
		for (int i = 0; i < percentages.length; i++) {
			if (percentages[i] < 0) {
				throw new IllegalArgumentException("percentages must be > 0, not " + percentages[i]);
			}
			sum += percentages[i];
		}
		int next = r.nextInt(sum);
		int count = 0;
		for (int i = 0; i < percentages.length; i++) {
			count += percentages[i];
			if (next < count) {
				return i;
			}
		}
		// Cannot logically happen
		return -1;
	}

	/**
	 * Return a random date within the specified date range. The offsets are relative to the current date
	 * so getDate(r, -30, -5, true) will return a date from 30 days ago to 5 days ago
	 * @param r
	 * @param startDayOffset
	 * @param endDayOffset
	 * @param removeTimeComponent
	 * @return
	 */
	public Date getDate(Random r, int startDayOffset, int endDayOffset, boolean removeTimeComponent) {
		Date currentDate = new Date();
		if (removeTimeComponent) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(currentDate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			currentDate = cal.getTime();
		}
		long now = currentDate.getTime();
		long start = now + TimeUnit.MILLISECONDS.convert(startDayOffset, TimeUnit.DAYS);
		long end = now + TimeUnit.MILLISECONDS.convert(endDayOffset, TimeUnit.DAYS);
		if (end < start) {
			long temp = start;
			start = end;
			end = temp;
		}
		if (end == start) {
			return new Date(start);
		}
		long diff = end - start;
		long next = r.nextLong();
		if (next < 0) {
			next = next + Long.MAX_VALUE;
		}
		return new Date(start + (next%diff) );
	}

	public String getCompanyName(Random r) {
		return getName(r) + " & Co";
	}

	public String getDomainName(Random r) {
		return getLastName(r) + ".com";
	}

	public String[] generateString(Random r, int numberOfStrings, int totalLength) {
		String[] results = new String[numberOfStrings];
		int lengthRemaining = totalLength;
		for (int i = 0; i < numberOfStrings; i++) {
			int chars;
			if (i == numberOfStrings -1) {
				chars = lengthRemaining;
			}
			else {
				chars = 1 + r.nextInt(lengthRemaining - (numberOfStrings-i));
			}
			results[i] = getString(r, chars, Alphabet.LOWER_LETTERS);
		}
		return results;
	}

	public String getIP(Random r) {
		String ip = String.valueOf(r.nextInt(255)+1);
		ip += "." + String.valueOf(r.nextInt(256));
		ip += "." + String.valueOf(r.nextInt(256));
		ip += "." + String.valueOf(r.nextInt(256));
		return ip;
	}

} // SeedUtils
