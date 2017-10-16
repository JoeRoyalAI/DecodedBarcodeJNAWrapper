package com.romaric.mcs.mgr.tote;

import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;

public class DecodedBarcodeJNAWrapper {
	
	private static final int BARCODE_SIZE = 10;
	
    // This is the standard, stable way of mapping, which supports extensive
    // customization and mapping of Java to native types.

    public interface DecodedBarcodeLibrary extends Library {
    	
        DecodedBarcodeLibrary INSTANCE = (DecodedBarcodeLibrary) Native.loadLibrary(("DecodedBarcode"), DecodedBarcodeLibrary.class);

        // NOOP for now.
        String imageAndDecode();
        int getDecodedBarcode(int toteIndex, int subToteIndex, Memory barcodes, short size);
        
    }

	public static void main(String[] args) {

		DecodedBarcodeLibrary.INSTANCE.imageAndDecode();

		Memory barcodes = new Memory(BARCODE_SIZE);
		
		barcodes.clear();

		for (int i = 0; i < 2; i++) {

			for (int j = 0; j < 6; j++) {

				String decodedBarcode = null;

				int barcodeSize = DecodedBarcodeLibrary.INSTANCE.getDecodedBarcode(0, j, barcodes, (short) BARCODE_SIZE);

				if (barcodeSize > 0) {
					barcodes.setByte(barcodeSize - 1, (byte) 0);
					decodedBarcode = barcodes.getString(0, "US_ASCII");
					System.out.printf("Decoded Barcode is %s... %n", decodedBarcode);
				}

			}

		}
		
		barcodes = null;

	}

}