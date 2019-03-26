package ouhk.comps380f.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.Material;
import ouhk.comps380f.view.DownloadingView;

@Controller
@RequestMapping("lecture")
public class LectureController {

    private volatile long LECTURE_ID_SEQUENCE = 1;
    private Map<Long, Material> lectureDatabase = new Hashtable<>();

    @RequestMapping(value = {"", "index"}, method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("lectureDatabase", lectureDatabase);
        return "index";
    }
    
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("add", "ticketForm", new Form());
    }

    public static class Form {

//        private String subject;
//        private String body;
        private List<MultipartFile> attachments;

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
        
        
    }
    
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public View create(Form form, Principal principal) throws IOException {
        Material material = new Material();
        material.setId(this.getNextTicketId());
//        material.setCustomerName(principal.getName());
//        ticket.setSubject(form.getSubject());
//        ticket.setBody(form.getBody());

        for (MultipartFile filePart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {
                material.addAttachment(attachment);
            }
        }
        this.lectureDatabase.put(material.getId(), material);
        return new RedirectView("/lecture/view/" + material.getId(), true);
    }

    private synchronized long getNextTicketId() {
        return this.LECTURE_ID_SEQUENCE ++;
    }
    
  @RequestMapping(
            value = "/{lectureId}/attachment/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View download(@PathVariable("lectureId") long lectureId,
            @PathVariable("attachment") String name) {
        Material note = this.lectureDatabase.get(lectureId);
        if (note != null) {
            Attachment attachment = note.getAttachment(name);
            if (attachment != null) {
                return new DownloadingView(attachment.getName(),
                        attachment.getMimeContentType(), attachment.getContents());
            }
        }
        return new RedirectView("/lecture/index", true);
    }
    
}
