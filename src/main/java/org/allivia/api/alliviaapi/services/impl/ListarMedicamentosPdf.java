package org.allivia.api.alliviaapi.services.impl;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.repositories.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ListarMedicamentosPdf {
    public static final Logger logger = LogManager.getLogger(ListarMedicamentosPdf.class);
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private IDoctorRepository iDoctorRepository;

    @Autowired
    private IFichaMedicaRepository iFichaMedicaRepository;

    @Autowired
    private IDiagnosticoRepository iDiagnosticoRepository;

    @Autowired
    private IFMedicamentosRepository IFMedicamentosRepository;

    @Autowired
    private IMedicamentosRepository iMedicamentosRepository;

    @Autowired
    private IEspecialidadDoctorRepository iEspecialidadDoctorRepository;

    @Autowired
    private IEspecialidadesRepository iEspecialidadesRepository;

    @Autowired
    private IAgendaCitaRepository iAgendaCitaRepository;

    public void export(HttpServletResponse response, Long idAgendacita) throws IOException {
        AppAgendacitaEntity cita = getRecetasPacienteByIdAgendacita(idAgendacita);
        logger.log(Level.INFO, "getRecetasPacienteByIdAgendacita: " + cita.toString());

        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        // Fonts
        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA);
        fontHeader.setSize(16);
        fontHeader.setColor(new Color(140, 140, 140));

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(24);

        Font fontSubtitle = FontFactory.getFont(FontFactory.HELVETICA);
        fontSubtitle.setSize(20);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(16);

        Font fontFooter = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE);
        fontFooter.setSize(14);

        // Paragraph
        Paragraph paragraph0 = new Paragraph("Allivia", fontHeader);
        paragraph0.getFirstLineIndent();
        paragraph0.setAlignment(Paragraph.ALIGN_RIGHT);

        Paragraph paragraph1 = new Paragraph("Receta No. "+cita.getId() + " - Matrícula: "+cita.getDoctor().getDescripcion(), fontTitle);
        paragraph1.setSpacingBefore(40);
        paragraph1.setAlignment(Paragraph.ALIGN_LEFT);

        String nomDoc = cita.getDoctor().getPerfilDoctor().getNombre() + " " + cita.getDoctor().getPerfilDoctor().getApellido();
        Paragraph paragraph2 = new Paragraph("Dr. "+ nomDoc, fontTitle);
        //Paragraph paragraph2_1 = new Paragraph("Matrícula: "+cita.getDoctor().getDescripcion(), fontSubtitle);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
        //paragraph2_1.setAlignment(Paragraph.ALIGN_RIGHT);

        String especialidades = "";
        int sizeEspecialidades = cita.getDoctor().getListEspecialidad().size();
        for (int i=0;i<sizeEspecialidades;i++) {
            if ( i > 0 )
                especialidades += " - ";
            especialidades += cita.getDoctor().getListEspecialidad().get(i).getDescripcion();
        }
        Paragraph paragraph3 = new Paragraph(especialidades, fontTitle);
        paragraph3.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph paragraph4 = new Paragraph("Diagnóstico", fontSubtitle);
        paragraph4.setSpacingBefore(20);
        paragraph4.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph paragraph4_1 = new Paragraph(cita.getDiagnostico().getDescripcion(), fontParagraph);
        paragraph4_1.setSpacingBefore(10);
        paragraph4_1.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph paragraph5 = new Paragraph("Medicamentos", fontSubtitle);
        paragraph5.setSpacingBefore(20);
        paragraph5.setSpacingAfter(10);
        paragraph5.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(paragraph0);
        document.add(paragraph1);
        document.add(paragraph2);
        //document.add(paragraph2_1);
        document.add(paragraph3);
        document.add(paragraph4);
        document.add(paragraph4_1);
        document.add(paragraph5);

        int height = 10;
        for (AppFichamedicamentosEntity medicamentos: cita.getMedicamentos()) {
            if (medicamentos.getMedicamento() != null) {
                Paragraph paragraphMedicamentos = new Paragraph(medicamentos.getMedicamento().get().getProducto() + ", " + medicamentos.getDosis() + ", " + medicamentos.getCantidad()+ "[uds]", fontParagraph);
                paragraphMedicamentos.setAlignment(Paragraph.ALIGN_LEFT);
                height += fontParagraph.getSize()+6;
                document.add(paragraphMedicamentos);
            } else {
                Paragraph paragraphMedicamentos = new Paragraph(medicamentos.getNombreMedicamento(), fontParagraph);
                paragraphMedicamentos.setAlignment(Paragraph.ALIGN_LEFT);
                height += fontParagraph.getSize()+6;
                document.add(paragraphMedicamentos);
            }
        }

        Paragraph paragraph6 = new Paragraph("Indicaciones", fontSubtitle);
        paragraph6.setSpacingBefore(20);
        paragraph6.setSpacingAfter(10);
        paragraph6.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(paragraph6);

        for (AppFichamedicamentosEntity medicamentos: cita.getMedicamentos()) {
            if (medicamentos.getMedicamento() != null) {
                String indicacion = medicamentos.getMedicamento().get().getProducto() + " - " + medicamentos.getIndicaciones() + " - " + medicamentos.getDuracion();
                Paragraph medicamentoIndicaciones = new Paragraph(indicacion, fontParagraph);
                medicamentoIndicaciones.setAlignment(Paragraph.ALIGN_LEFT);
                document.add(medicamentoIndicaciones);
            } else {
                String indicacion = medicamentos.getNombreMedicamento() + " - " + medicamentos.getIndicaciones() + " - " + medicamentos.getDuracion();
                Paragraph medicamentoIndicaciones = new Paragraph(indicacion, fontParagraph);
                medicamentoIndicaciones.setAlignment(Paragraph.ALIGN_LEFT);
                document.add(medicamentoIndicaciones);
            }
        }

        // Paragraph paragraph4 = new Paragraph("This is a paragraph.", fontParagraph);
        // paragraph4.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph paragraph7 = new Paragraph("Recuerda que esta receta es válida por 7 días.", fontFooter);
        paragraph7.setSpacingBefore(50);
        paragraph7.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(paragraph7);

        // Firma
        if ( cita.getDoctor().getFirma() != null && cita.getDoctor().getFirma() != "" ) {
            Path fileStorageLocation = Paths.get("D:/AlliviaImagenes/").toAbsolutePath().normalize();
            String archivo;
            if ( cita.getDoctor().getFirma().contains("FIRMA/") ) {
                archivo = cita.getDoctor().getFirma().split("FIRMA/")[1];
            } else {
                archivo = cita.getDoctor().getFirma();
            }
            Path targetLocation = fileStorageLocation.resolve("firma/" + archivo);
            Image png = Image.getInstance(targetLocation.toString());
            png.setAbsolutePosition(330, 40);
            png.scaleToFit(180, 100);
            document.add(png);
        }

        // LINEAS Y TEXTO FIJOS
        PdfContentByte cb = writer.getDirectContent();
        cb.setLineWidth(1f);
        cb.setRGBColorStroke(160, 160, 160);
        cb.moveTo(37, 770);
        cb.lineTo(560, 770);
        cb.stroke();

        cb.setLineWidth(2f);
        cb.setRGBColorStroke(0,0,0);
        cb.moveTo(37, 620);
        cb.lineTo(560, 620);
        cb.stroke();

        cb.setLineWidth(1f);
        cb.setRGBColorStroke(100,100,100);
        cb.moveTo(37, 577);
        cb.lineTo(560, 577);
        cb.stroke();

        cb.moveTo(37, 493);
        cb.lineTo(560, 493);
        cb.stroke();

        cb.moveTo(37, 439-height);
        cb.lineTo(560, 439-height);
        cb.stroke();

        // TEXTO FIJO
        cb.beginText();
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        cb.setFontAndSize(bf, 15);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, cita.getFecha().split(" ")[0], 130, 45, 0);

        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Fecha", 150, 30, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Firma", 450, 30, 0);
        cb.endText();


        cb.sanityCheck();

        // Close document
        document.close();
    }

    public AppAgendacitaEntity getRecetasPacienteByIdAgendacita(Long idAgendacita) {
        AppAgendacitaEntity appAgendacitaEntity = iAgendaCitaRepository.findAgendaCitaById(idAgendacita);

        logger.log(Level.INFO, "Obtener medicamentos de una cita: " + appAgendacitaEntity.toString());
        AppDoctorEntity appDoctorEntity = iDoctorRepository.findById(appAgendacitaEntity.getIdDoctor()).get();
        AppPacienteEntity appPacienteEntity = pacienteRepository.findById(appAgendacitaEntity.getIdPaciente()).get();
        AppFichamedicaEntity appFichamedicaEntity = iFichaMedicaRepository.findByIdAgendacita(appAgendacitaEntity.getId());
        AppFichadiagnosticoEntity appFichadiagnosticoEntity = iDiagnosticoRepository.findByIdFichamedica(appFichamedicaEntity.getId());
        AppUsuarioEntity appUsuarioEntity = userRepository.findById(appDoctorEntity.getUsuarioId()).get();
        AppUsuarioEntity appUsuarioPaciente = userRepository.findById(appPacienteEntity.getUsuarioId()).get();
        List<AppDoctoresespecialidadesEntity> appEspecialidadEntityList = iEspecialidadDoctorRepository.findByIdDoctor(appDoctorEntity.getId());


        List<AppFichamedicamentosEntity> appFichamedicamentosEntityList = IFMedicamentosRepository.findByIdFichamedica(appFichamedicaEntity.getId());
        List<AppEspecialidadEntity> appEspecialidadEntities = new ArrayList<>();
        for (AppDoctoresespecialidadesEntity rowEspecialidadDoctor : appEspecialidadEntityList) {
            AppEspecialidadEntity especialidadEntity = iEspecialidadesRepository.findById(rowEspecialidadDoctor.getIdEspecialidad()).get();
            appEspecialidadEntities.add(especialidadEntity);
        }
        for (AppFichamedicamentosEntity rowFichamedicamentos : appFichamedicamentosEntityList) {
            if (rowFichamedicamentos.getIdMedicamento() != null) {
                Optional<AppMedicamentosEntity> appMedicamentosEntity = iMedicamentosRepository.findById(rowFichamedicamentos.getIdMedicamento());
                rowFichamedicamentos.setMedicamento(appMedicamentosEntity);
            }
        }

        appPacienteEntity.setPerfilPaciente(appUsuarioPaciente);
        appDoctorEntity.setPerfilDoctor(appUsuarioEntity);
        appDoctorEntity.setListEspecialidad(appEspecialidadEntities);
        appAgendacitaEntity.setDoctor(appDoctorEntity);
        appAgendacitaEntity.setPaciente(appPacienteEntity);
        appAgendacitaEntity.setMedicamentos(appFichamedicamentosEntityList);
        appAgendacitaEntity.setDiagnostico(appFichadiagnosticoEntity);

        return appAgendacitaEntity;
    }
}
