package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobData;
import org.launchcode.models.forms.JobForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 * Edited by Brian McGirk
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String index(Model model, @RequestParam int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job job = jobData.findById(id);

        model.addAttribute("jobs", job);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors, RedirectAttributes redirectAttributes, @RequestParam String name) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if(errors.hasErrors()){
            return "new-job";
        }

        int employerId = jobForm.getEmployerId();
        int locationId = jobForm.getLocationId();
        int positionId = jobForm.getPositionTypeId();
        int skillId = jobForm.getCoreCompetencyId();

        String aName = name;
        Employer aEmployer = jobData.getEmployers().findById(employerId);
        Location aLocation = jobData.getLocations().findById(locationId);
        PositionType aPositionType = jobData.getPositionTypes().findById(positionId);
        CoreCompetency aSkill = jobData.getCoreCompetencies().findById(skillId);


        Job newJob = new Job(aName, aEmployer, aLocation, aPositionType, aSkill);

        jobData.add(newJob);
        model.addAttribute("jobs", newJob);
        redirectAttributes.addAttribute("id", newJob.getId());

        return "redirect:";

    }
}