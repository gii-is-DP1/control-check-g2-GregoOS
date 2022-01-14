package org.springframework.samples.petclinic.feeding;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javassist.bytecode.stackmap.BasicBlock.Catch;

@Controller
@RequestMapping("/feeding")
public class FeedingController {

    @Autowired
    private FeedingService fs;

    @Autowired
    private PetService ps;

    private static final String VIEWS_PRODUCT_CREATE_OR_UPDATE_FORM = "feedings/createOrUpdateFeedingForm";

    @GetMapping(path = "/create")
    public String initCreationForm(ModelMap modelmap) {
        String view = VIEWS_PRODUCT_CREATE_OR_UPDATE_FORM;
        modelmap.addAttribute("feeding", new Feeding());
        modelmap.addAttribute("feedingTypes", fs.getAllFeedingTypes());
        modelmap.addAttribute("pets", ps.getAllPets());
        return view;
    }

    @PostMapping(path = "/create")
    public String processCreationForm(@Valid Feeding feeding, BindingResult result, ModelMap modelmap)
            throws UnfeasibleFeedingException {
        if (result.hasErrors()) {
            modelmap.addAttribute("feeding", new Feeding());
            modelmap.addAttribute("feedingTypes", fs.getAllFeedingTypes());
            modelmap.addAttribute("pets", ps.getAllPets());
            return VIEWS_PRODUCT_CREATE_OR_UPDATE_FORM;
        } else {
            try {
                fs.save(feeding);
            } catch (Exception e) {
                modelmap.addAttribute("feeding", new Feeding());
                modelmap.addAttribute("feedingTypes", fs.getAllFeedingTypes());
                modelmap.addAttribute("pets", ps.getAllPets());
                return VIEWS_PRODUCT_CREATE_OR_UPDATE_FORM;
            }

            modelmap.addAttribute("message", "Product saved");
        }
        return "redirect:/welcome";
    }
}
