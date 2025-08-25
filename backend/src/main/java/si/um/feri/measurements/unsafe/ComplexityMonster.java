package si.um.feri.measurements.unsafe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Intentionally bad & complex code for Sonar demo.
 * Expect: high cognitive complexity, code smells, resource handling issues, long method, magic numbers, etc.
 */
public class ComplexityMonster {

    private static final Logger LOG = Logger.getLogger(ComplexityMonster.class.getName());

    // Mutable static state (thread-unsafe)
    private static Map<String, Integer> GLOBAL_CACHE = new HashMap<>();

    // Long parameter list + unused params
    public String process(String a, String b, String c, String d, String e, String f, String g) {
        // dead code & magic numbers
        if ("noop".equals(a)) {
            return "noop" + 42;
        }
        return compute(a, b);
    }

    // Way too long and complex method
    public String compute(String key, String mode) {
        String result = "";
        List<String> lines = new ArrayList<>();
        BufferedReader br = null; // resource not closed in all paths

        try {
            // Reading a file that may not exist; duplicated code below
            br = new BufferedReader(new FileReader("config.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException ex) {
            // Swallowing exception
            LOG.warning("Could not read config: " + ex.getMessage());
        }

        // Excessive nesting + repeated patterns
        if (mode != null) {
            if (mode.equalsIgnoreCase("A")) {
                if (key != null) {
                    if (key.length() > 0) {
                        if (key.startsWith("!")) {
                            result = reverse(key.substring(1));
                        } else {
                            if (key.contains(",")) {
                                String[] parts = key.split(",");
                                int sum = 0;
                                for (int i = 0; i < parts.length; i++) {
                                    for (int j = 0; j < parts[i].length(); j++) {
                                        char ch = parts[i].charAt(j);
                                        if (Character.isDigit(ch)) {
                                            sum += ch - '0';
                                            if (sum > 1000) {
                                                // magic threshold + deep nesting
                                                if (sum % 2 == 0) {
                                                    if (sum % 3 == 0) {
                                                        result = "FizzBuzz";
                                                    } else {
                                                        result = "Fizz";
                                                    }
                                                } else {
                                                    if (sum % 5 == 0) {
                                                        result = "Buzz";
                                                    } else {
                                                        result = "Odd";
                                                    }
                                                }
                                            }
                                        } else if (Character.isLetter(ch)) {
                                            sum += Character.toUpperCase(ch) - 'A' + 1;
                                        } else {
                                            // do nothing
                                        }
                                    }
                                }
                                if (result.isEmpty()) {
                                    result = "SUM=" + sum;
                                }
                            } else {
                                result = key.toUpperCase();
                            }
                        }
                    } else {
                        result = "EMPTY";
                    }
                } else {
                    result = "NULL";
                }
            } else if (mode.equalsIgnoreCase("B")) {
                // Duplicated logic (copy-paste of A with tiny changes)
                if (key != null) {
                    if (key.length() > 0) {
                        if (key.endsWith("!")) {
                            result = reverse(key.substring(0, key.length() - 1));
                        } else {
                            if (key.contains(";")) {
                                String[] parts = key.split(";");
                                int prod = 1;
                                for (int i = 0; i < parts.length; i++) {
                                    for (int j = 0; j < parts[i].length(); j++) {
                                        char ch = parts[i].charAt(j);
                                        if (Character.isDigit(ch)) {
                                            prod *= (ch - '0' + 1); // arbitrary
                                            if (prod > 500) {
                                                if (prod % 2 == 0) {
                                                    result = "BIG-EVEN";
                                                } else {
                                                    if (prod % 3 == 0) {
                                                        result = "BIG-THREE";
                                                    } else {
                                                        result = "BIG";
                                                    }
                                                }
                                            }
                                        } else {
                                            // ignore
                                        }
                                    }
                                }
                                if (result.isEmpty()) {
                                    result = "PROD=" + prod;
                                }
                            } else {
                                result = key.toLowerCase(Locale.ROOT);
                            }
                        }
                    } else {
                        result = "EMPTY";
                    }
                } else {
                    result = "NULL";
                }
            } else if (mode.equalsIgnoreCase("C")) {
                // Switch with missing default & fallthrough smell
                int code = classify(key);
                switch (code) {
                    case 0:
                        result = "ZERO";
                        // missing break (fallthrough)
                    case 1:
                        result = (result.isEmpty() ? "" : result + "-") + "ONE";
                        break;
                    case 2:
                        if (key != null && key.length() > 2) {
                            if (key.charAt(0) == key.charAt(1)) {
                                if (key.charAt(1) == key.charAt(2)) {
                                    result = "TRIPLE";
                                } else {
                                    result = "DOUBLE";
                                }
                            } else {
                                if (key.contains("x")) {
                                    if (key.contains("y")) {
                                        if (key.contains("z")) {
                                            result = "XYZ";
                                        } else {
                                            result = "XY";
                                        }
                                    } else {
                                        result = "X";
                                    }
                                } else {
                                    result = "OTHER";
                                }
                            }
                        } else {
                            result = "SHORT";
                        }
                        break;
                }
            } else {
                // Re-run file read (duplicate I/O) and recompute
                List<String> again = new ArrayList<>();
                try (BufferedReader br2 = new BufferedReader(new FileReader("config.txt"))) {
                    String l;
                    while ((l = br2.readLine()) != null) {
                        again.add(l.trim());
                    }
                } catch (IOException e) {
                    // swallowed
                }
                result = "MODE-UNKNOWN(" + mode + ")-" + again.size();
            }
        } else {
            result = fallbackCompute(key); // recursion below can blow up
        }

        // Unnecessary complex post-processing
        if (result != null && !result.isEmpty()) {
            if (GLOBAL_CACHE.containsKey(result)) {
                GLOBAL_CACHE.put(result, GLOBAL_CACHE.get(result) + 1);
            } else {
                GLOBAL_CACHE.put(result, 1);
            }
            if (GLOBAL_CACHE.get(result) > 5) {
                LOG.info("Popular: " + result);
            } else if (GLOBAL_CACHE.get(result) > 3) {
                LOG.info("Trending: " + result);
            } else if (GLOBAL_CACHE.get(result) > 1) {
                LOG.info("Seen: " + result);
            } else {
                // nothing
            }
        }

        // resource leak: br may be left open if success path (intentionally not closed)
        return result;
    }

    // Needless recursion + no clear base case for some inputs
    private String fallbackCompute(String key) {
        if (key == null) return "N/A";
        if (key.length() < 2) {
            return key + fallbackCompute(key + "x"); // can recurse a lot
        }
        return key;
    }

    // Utility with duplicated logic and magic numbers
    private int classify(String s) {
        if (s == null) return -1;
        if (s.isEmpty()) return 0;
        if (s.length() == 1) return 1;
        if (s.length() == 2) return 2;
        if (s.length() == 3) return 2; // duplicate branch to confuse
        if (s.length() > 10) return 2;
        return 1;
    }

    private String reverse(String s) {
        char[] arr = s.toCharArray();
        for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
            char tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return new String(arr);
    }
}