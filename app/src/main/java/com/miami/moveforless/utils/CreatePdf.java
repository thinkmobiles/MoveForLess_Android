package com.miami.moveforless.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.miami.moveforless.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by SetKrul on 30.10.2015.
 */
public class CreatePdf {
    Context mContext;
    Document document;

    public CreatePdf(Context _context) {
        this.mContext = _context;
    }

    public void createPdf() throws DocumentException, IOException {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath());
        File file = new File(dir, "sample2.pdf");
        document = new Document(PageSize.A4, 35, 35, 35, 35);

        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        document.add(addHeader());
        document.add(addSpace(15f));
        document.add(addAttention());
        document.add(addSpace(15f));
        document.add(addFromTo());
        document.add(addSpace(15f));
        document.add(addDetails());
        document.add(addSpace(15f));
        document.add(addNotes());
        document.add(addSpace(30f));
        document.add(addTextLine("PLEASE READ CAREFULLY:", FontFactory.getFont(FontFactory.TIMES, 12, Font.BOLD,
                BaseColor.RED), Element.ALIGN_LEFT));
        document.add(addTextLine((mContext.getResources().getString(R.string.please_read_carefully)), FontFactory
                .getFont(FontFactory.TIMES, 8, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_JUSTIFIED));
        document.add(addSpace(50f));
        document.add(addSign());
        document.close();
    }

    public PdfPTable addHeader() {
        PdfPTable table = new PdfPTable(new float[]{1, 1.5f, 1});
        table.setWidthPercentage(100);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell cell;
        cell = new PdfPCell();
        cell = setBoarder(cell, 0f);
        cell.setImage(getImage(mContext, R.drawable.logo, 237f, 48f));
        cell.setPadding(10f);
        table.addCell(cell);

        cell = new PdfPCell(addTableToCell(new float[]{1}, Element.ALIGN_CENTER, new PdfPCell[]{setPadding(setBoarder
                (newCell(mContext.getResources().getString(R.string.move_for_less), FontFactory.getFont(FontFactory
                        .TIMES, 12, Font.NORMAL, BaseColor.BLUE), Element.ALIGN_CENTER), 0f), 0f), setPadding
                (setBoarder(newCell(mContext.getResources().getString(R.string.company_requisites), FontFactory
                                .getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_CENTER), 0f),
                        0f)}));
        table.addCell(setBoarder(cell, 0f));

        cell = new PdfPCell(addTableToCell(new float[]{1}, Element.ALIGN_LEFT, new PdfPCell[]{setPadding(setBoarder
                (newCell(" " + mContext.getResources().getString(R.string.job_number), FontFactory.getFont
                        (FontFactory.TIMES, 10, Font.NORMAL, BaseColor.RED), Element.ALIGN_LEFT), 0f), 0f),
                setBoarder(newCell(mContext.getResources().getString(R.string.client_requisites), FontFactory.getFont
                        (FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_LEFT), 0f)}));
        table.addCell(setBoarder(cell, 0f));
        return table;
    }

    public PdfPTable addAttention() {
        PdfPTable table = new PdfPTable(new float[]{1});
        table.setWidthPercentage(100);
        PdfPCell cell;
        cell = setBoarder(newCell(mContext.getResources().getString(R.string.attention), FontFactory.getFont
                (FontFactory.TIMES, 9, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_JUSTIFIED), 0f, 0f, 1f, 1f);
        table.addCell(cell);
        return table;
    }

    public PdfPTable addFromTo() {
        PdfPTable table = new PdfPTable(new float[]{1, 1});
        table.setWidthPercentage(100);
        PdfPCell cell;
        cell = new PdfPCell(addTableToCell(new float[]{0.3f, 1, 1}, Element.ALIGN_LEFT, new PdfPCell[]{newCell
                ("From:" + " ", FontFactory.getFont(FontFactory.TIMES, 10, Font.BOLD, BaseColor.WHITE), Element
                        .ALIGN_LEFT, "#0086DA"), setBoarder(newCell("Date Pickup Serv. Req'd: ", FontFactory.getFont
                (FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_LEFT), 0f), setBoarder(newCell
                ("09/08/2015 " +
                        "@" + " 09:00 AM", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.RED), Element
                        .ALIGN_LEFT), 0f)}));
        table.addCell(cell);

        cell = new PdfPCell(addTableToCell(new float[]{0.3f, 1, 1}, Element.ALIGN_LEFT, new PdfPCell[]{newCell("To: "
                        + "", FontFactory.getFont(FontFactory.TIMES, 10, Font.BOLD, BaseColor.WHITE), Element.ALIGN_LEFT,
                "#0086DA"), setBoarder(newCell("Date Pickup Serv. Req'd: ", FontFactory.getFont(FontFactory.TIMES,
                10, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_LEFT), 0f), setBoarder(newCell("09/08/2015 " +
                "@" + " 02:30 PM", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.RED), Element
                .ALIGN_LEFT), 0f)}));
        table.addCell(cell);

        cell = setBoarder(newCell("Antony Nunez", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor
                .BLACK), Element.ALIGN_LEFT), 0f, 0.5f, 0f, 0.5f);
        table.addCell(cell);

        cell = setBoarder(newCell("Antony Nunez", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor
                .BLACK), Element.ALIGN_LEFT), 0.5f, 0f, 0f, 0.5f);
        table.addCell(cell);

        cell = new PdfPCell(addTableToCell(new float[]{1, 1}, Element.ALIGN_LEFT, new PdfPCell[]{setBoarder(newCell
                ("50 Biscayne Blvd*ele", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK),
                        Element.ALIGN_LEFT), 0f), setBoarder(newCell("Apt. No.:4902", FontFactory.getFont(FontFactory
                .TIMES, 10, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_LEFT), 0f)}));
        table.addCell(setBoarder(cell, 0f, 0.5f, 0f, 0.5f));

        cell = new PdfPCell(addTableToCell(new float[]{1, 1}, Element.ALIGN_LEFT, new PdfPCell[]{setBoarder(newCell
                ("611 Enclave E Cir*ground", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK)
                        , Element.ALIGN_LEFT), 0f), setBoarder(newCell("Apt. No.:", FontFactory.getFont(FontFactory
                .TIMES, 10, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_LEFT), 0f)}));
        table.addCell(setBoarder(cell, 0.5f, 0f, 0f, 0.5f));

        cell = setBoarder(newCell("MIAMI, FL, 33132", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL,
                BaseColor.BLACK), Element.ALIGN_LEFT), 0f, 0.5f, 0f, 0.5f);
        table.addCell(cell);

        cell = setBoarder(newCell("HOLLYWOOD, FL, 33027", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL,
                BaseColor.BLACK), Element.ALIGN_LEFT), 0.5f, 0f, 0f, 0.5f);
        table.addCell(cell);

        cell = setBoarder(newCell("786-251-5601", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor
                .BLACK), Element.ALIGN_LEFT), 0f, 0.5f, 0f, 0.5f);
        table.addCell(cell);

        cell = setBoarder(newCell("786-251-5601", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor
                .BLACK), Element.ALIGN_LEFT), 0.5f, 0f, 0f, 0.5f);
        table.addCell(cell);
        return table;
    }

    public PdfPTable addDetails() {
        PdfPTable table = new PdfPTable(new float[]{1, 1});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidthPercentage(100);

        table.addCell(newCell("ESTIMATED TOTALS", FontFactory.getFont(FontFactory.TIMES, 9, Font.NORMAL, BaseColor
                .WHITE), Element.ALIGN_CENTER, "#0086DA"));

        table.addCell(newCell("ACTUAL TOTALS", FontFactory.getFont(FontFactory.TIMES, 9, Font.NORMAL, BaseColor
                .WHITE), Element.ALIGN_CENTER, "#0086DA"));


        table.addCell(addTableToCell(new float[]{2, 1}, Element.ALIGN_RIGHT, new PdfPCell[]{newCell("ADDITIONAL " +
                "SERVICES", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK), Element
                .ALIGN_RIGHT), newCell("0" + ".00", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor
                .BLACK), Element.ALIGN_RIGHT)}));
        table.addCell(addTableToCell(new float[]{2, 1}, Element.ALIGN_RIGHT, new PdfPCell[]{newCell("ADDITIONAL " +
                "SERVICES", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK), Element
                .ALIGN_RIGHT), newCell("0" + ".00", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor
                .BLACK), Element.ALIGN_RIGHT)}));

        table.addCell(addTableToCell(new float[]{2, 1}, Element.ALIGN_RIGHT, new PdfPCell[]{newCell("LABOR CHARGE for" +
                        " 3 Men + 1 Truck @ 5 " +
                        "" + "hours x 95 ea", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK),
                Element.ALIGN_RIGHT, "#a9a8a8"), newCell("475.00", FontFactory.getFont(FontFactory.TIMES, 10, Font
                .NORMAL, BaseColor.BLACK), Element.ALIGN_RIGHT, "#a9a8a8")}));
        table.addCell(addTableToCell(new float[]{2, 1}, Element.ALIGN_RIGHT, new PdfPCell[]{newCell("LABOR",
                FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_RIGHT,
                "#a9a8a8"), newCell("0" + ".00", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor
                .BLACK), Element.ALIGN_RIGHT, "#a9a8a8")}));

        table.addCell(addTableToCell(new float[]{2, 1}, Element.ALIGN_RIGHT, new PdfPCell[]{newCell("FUEL CHARGE",
                FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_RIGHT,
                "#a9a8a8"), newCell("47" + ".50", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor
                .BLACK), Element.ALIGN_RIGHT, "#a9a8a8")}));
        table.addCell(addTableToCell(new float[]{2, 1}, Element.ALIGN_RIGHT, new PdfPCell[]{newCell("FUEL CHARGE",
                FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_RIGHT,
                "#a9a8a8"), newCell("0" + ".00", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor
                .BLACK), Element.ALIGN_RIGHT, "#a9a8a8")}));

        table.addCell(addTableToCell(new float[]{2, 1}, Element.ALIGN_RIGHT, new PdfPCell[]{newCell("QUOTE TOTAL",
                FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_RIGHT),
                newCell("522.50", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK), Element
                        .ALIGN_RIGHT, "#D6BF14")}));
        table.addCell(addTableToCell(new float[]{2, 1}, Element.ALIGN_RIGHT, new PdfPCell[]{newCell("QUOTE TOTAL",
                FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_RIGHT),
                newCell("0" + "" +
                        ".00", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_RIGHT)
        }));
        return table;
    }


    public PdfPTable addNotes() {
        PdfPTable table = new PdfPTable(new float[]{1});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidthPercentage(100);
        table.addCell(newCell("CUSTOMER NOTES", FontFactory.getFont(FontFactory.TIMES, 9, Font.NORMAL, BaseColor
                .WHITE), Element.ALIGN_CENTER, "#0086DA"));
        table.addCell(newCell("$95 deposit paid", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor
                .BLACK), Element.ALIGN_LEFT));
        return table;
    }

    public PdfPTable addSign() {
        PdfPTable table = new PdfPTable(new float[]{1, 1, 1});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_BOTTOM);
        table.setWidthPercentage(100);

        PdfPCell cell;

        cell = new PdfPCell(addTableToCell(new float[]{1, 0.3f}, Element.ALIGN_BOTTOM, new PdfPCell[]{setBoarder
                (newCell("Customer Service", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK)
                        , Element.ALIGN_BOTTOM), 0f, 0f, 0f, 0.5f), setBoarder(newCell(null, null, 0), 0f)}));
        cell.setPaddingTop(30f);
        table.addCell(setBoarder(cell, 0f));

//        cell = new PdfPCell();
//        cell = setBoarder(cell, 0f, 0f, 0f, 0.5f);
//        cell.setImage(getImage(mContext, R.drawable.logo, 237f, 48f));
//        table.addCell(cell);

        cell = new PdfPCell(addTableToCell(new float[]{1}, Element.ALIGN_BOTTOM, new PdfPCell[]{setBoarder(newCell
                (null, null, 0), 0f), setBoarder(newCellWithImage(mContext, R.drawable.logo, 237f, 48f), 0f, 0f, 0f,
                0.5f)}));
        table.addCell(setBoarder(cell, 0f));

        cell = new PdfPCell(addTableToCell(new float[]{0.3f, 1}, Element.ALIGN_BOTTOM, new PdfPCell[]{setBoarder
                (newCell(null, null, 0), 0f), setBoarder(newCell("09/08/2015", FontFactory.getFont(FontFactory.TIMES,
                10, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_BOTTOM), 0f, 0f, 0f, 0.5f)}));
        cell.setPaddingTop(30f);
        table.addCell(setBoarder(cell, 0f));

        cell = new PdfPCell(addTableToCell(new float[]{1, 0.3f}, Element.ALIGN_BOTTOM, new PdfPCell[]{setBoarder
                (newCell("Customer Name", FontFactory.getFont(FontFactory.TIMES, 10, Font.NORMAL, BaseColor.BLACK),
                        Element.ALIGN_BOTTOM), 0f), setBoarder(newCell(null, null, 0), 0f)}));
        table.addCell(setBoarder(cell, 0f));

        cell = setBoarder(newCell(null, null, 0), 0f);
        table.addCell(setBoarder(cell, 0f));

        cell = new PdfPCell(addTableToCell(new float[]{0.3f, 1}, Element.ALIGN_BOTTOM, new PdfPCell[]{setBoarder
                (newCell(null, null, 0), 0f), setBoarder(newCell("Date", FontFactory.getFont(FontFactory.TIMES,
                10, Font.NORMAL, BaseColor.BLACK), Element.ALIGN_BOTTOM), 0f)}));
        table.addCell(setBoarder(cell, 0f));

        return table;
    }


    public PdfPCell setBoarder(PdfPCell _cell, float _left, float _right, float _top, float _bottom) {
        _cell.setBorderWidthLeft(_left);
        _cell.setBorderWidthBottom(_bottom);
        _cell.setBorderWidthRight(_right);
        _cell.setBorderWidthTop(_top);
        return _cell;
    }

    public PdfPCell setBoarder(PdfPCell _cell, float _boarder) {
        _cell.setBorderWidth(_boarder);
        return _cell;
    }

    public PdfPCell setPadding(PdfPCell _cell, float _left, float _right, float _top, float _buttom) {
        _cell.setPaddingLeft(_left);
        _cell.setPaddingBottom(_buttom);
        _cell.setPaddingRight(_right);
        _cell.setPaddingTop(_top);
        return _cell;
    }

    public PdfPCell setPadding(PdfPCell _cell, float _padding) {
        _cell.setPadding(_padding);
        return _cell;
    }

    public PdfPCell addTableToCell(float[] _floats, int _align, PdfPCell[] _pdfPCell) {
        PdfPTable table = new PdfPTable(_floats);
        table.getDefaultCell().setHorizontalAlignment(_align);
        table.setWidthPercentage(100);
        for (PdfPCell a_pdfPCell : _pdfPCell) {
            table.addCell(a_pdfPCell);
        }
        PdfPCell cell;
        cell = new PdfPCell(table);
        cell.setPadding(0f);
        return cell;
    }

    public PdfPCell newCell(String _s, Font _font, int _align, String _color) {
        PdfPCell cell;
        cell = new PdfPCell(new Phrase(_s, _font));
        cell.setHorizontalAlignment(_align);
        if (!TextUtils.isEmpty(_color)) {
            cell.setBackgroundColor(WebColors.getRGBColor(_color));
        }
        return cell;
    }

    public PdfPCell newCell(String _s, Font _font, int _alignCenter) {
        PdfPCell cell;
        cell = new PdfPCell(new Phrase(_s, _font));
        cell.setHorizontalAlignment(_alignCenter);
        return cell;
    }

    public PdfPCell newCellWithImage(Context _context, int _logo, float _height, float _width) {
        PdfPCell cell;
        cell = new PdfPCell();
        cell.setImage(getImage(_context, _logo, _height, _width));
        return cell;
    }


    public Paragraph addSpace(float _space) {
        Paragraph paragraph = new Paragraph(_space);
        paragraph.setSpacingBefore(_space);
        return paragraph;
    }

    public Paragraph addTextLine(String s, Font _font, int _alignLeft) {
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(_alignLeft);
        paragraph.add(new Anchor(s, _font));
        return paragraph;
    }

    public Image getImage(Context _context, int _image, float _height, float _width) {
        Drawable d = ContextCompat.getDrawable(_context, _image);
        BitmapDrawable bitDw = ((BitmapDrawable) d);
        Bitmap bmp = bitDw.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image image = null;
        try {
            image = Image.getInstance(stream.toByteArray());
        } catch (BadElementException | IOException e) {
            e.printStackTrace();
        }
        if (image != null) {
            image.scaleAbsolute(_height, _width);
        }
        return image;
    }
}
