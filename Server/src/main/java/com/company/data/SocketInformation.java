package com.company.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.PrintWriter;

@Data
@AllArgsConstructor
public class SocketInformation {
    // this will later have to hold the username as well
    private BufferedReader reader;
    private PrintWriter writer;
}
