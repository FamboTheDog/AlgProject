package com.company.data

import java.io.BufferedReader
import java.io.PrintWriter
import java.net.DatagramSocket
import java.net.InetAddress

data class UserCommunication(var reader: BufferedReader,
                             var writer: PrintWriter,
                             var UDPSocket: DatagramSocket,
                             var adres: InetAddress,
                             var buffer: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserCommunication

        if (reader != other.reader) return false
        if (writer != other.writer) return false
        if (UDPSocket != other.UDPSocket) return false
        if (adres != other.adres) return false
        if (!buffer.contentEquals(other.buffer)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = reader.hashCode()
        result = 31 * result + writer.hashCode()
        result = 31 * result + UDPSocket.hashCode()
        result = 31 * result + adres.hashCode()
        result = 31 * result + buffer.contentHashCode()
        return result
    }
}