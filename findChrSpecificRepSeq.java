import java.io.*;
import java.util.Hashtable;
import java.util.Iterator;

public class findChrSpecificRepSeq {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		StringBuffer[] chrs = new StringBuffer[22]; ## Mouse chrs from 1-19,X,Y,M

		for (int i = 0; i < 22; i++) {
			int c = i + 1;
			chrs[i] = fasta2stringbuffer("mm10/chr" + c + ".fa"); ## 19=X,20=Y,21=M 
		}

		Hashtable<String, Integer>[] chrSpecificRepSeq = new Hashtable[22];

		for (int t = 0; t < 22; t++) {
			repseqchr[t] = new Hashtable<String, Integer>();
			for (int n = 0; n < chrs[t].length() - 23; n++) {
				String tmpStr = chrs[t].substring(n, n + 23);
				if (!tmpStr.matches(".*N.*")) {
					if (tmpStr.matches(".*GG") || tmpStr.matches("CC.*")) {
						if (numberOfStr(chrs[t], tmpStr) > 10) {
							int c = 0;
							for (int j = 0; j < t; j++) {
								if (numberOfStr(chrs[j], tmpStr) != 0) {
									c++;
								}
							}
							for (int j = t + 1; j < 22; j++) {
								if (numberOfStr(chrs[j], tmpStr) != 0) {
									c++;
								}
							}
							if (c == 0) {
								chrSpecificRepSeq[t].put(tmpStr, n);
							}
						}
					}
				}
			}
		}

		for (int t = 0; t < 22; t++) {
			for (Iterator it = chrSpecificRepSeq[t].keySet().iterator(); it.hasNext();) {
				String seq = (String) it.next();
				int pos = chrSpecificRepSeq[t].get(seq);
				System.out.println("chr" + t + "\t" + seq + "\t" + pos);
			}

		}

	}

	public static StringBuffer fasta2stringbuffer(String filepath) throws FileNotFoundException, IOException {
		StringBuffer chr = new StringBuffer();

		BufferedReader file = new BufferedReader(new FileReader(filepath));

		while (file.ready()) {
			String l = file.readLine();
			if (!l.matches("^>.*")) {
				chr.append(l.toUpperCase());
			}
		}
		file.close();
		return chr;
	}

	public static int numberOfStr(StringBuffer sb, String s) {
		int count = 0;
		int m = sb.indexOf(s);

		while (m != -1) {
			m = sb.indexOf(s, m + 1);
			count++;
		}

		return count;
	}

}
