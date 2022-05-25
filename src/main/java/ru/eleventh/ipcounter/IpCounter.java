package ru.eleventh.ipcounter;
package ru.eleventh.ipcounter;

import lombok.SneakyThrows;
import lombok.val;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

import static java.util.stream.Collectors.toList;

public class IpCounter {

    private static final int ARRAY_SIZE = 536870912; // 256 * 256 * 256 * (256 / 8)

    @SneakyThrows
    public static void main(String[] args) {
        val fileName = args[0];
        val presenceSign = new byte[ARRAY_SIZE];

        String ip;
        val reader = new BufferedReader(new FileReader(fileName));
        while ((ip = reader.readLine()) != null) {
            val segments = Arrays.stream(ip.split("\\.")).map(Integer::parseInt).collect(toList());
            int index = segments.get(0) << 21 | segments.get(1) << 13 | segments.get(2) << 5 | (segments.get(3) >> 3);
            int value = 1 << (segments.get(3) & 0b111);
            presenceSign[index] |= value;
        }

        var ans = 0;
        for (int i = 0; i < ARRAY_SIZE; i++) {
            if (presenceSign[i] != 0) {
                for (int j = 0; j < 8; j++) {
                    ans += presenceSign[i] >> j & 0x1;
                }
            }
        }

        System.out.println(ans);
    }
}
