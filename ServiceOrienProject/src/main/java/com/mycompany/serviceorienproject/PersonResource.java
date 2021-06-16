/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceorienproject;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.google.gson.Gson;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.PUT;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response.ResponseBuilder;
/**
 * REST Web Service
 *
 * @author major
 */
@Path("people")
public class PersonResource {
     @OPTIONS
    public Response doOptions(){
        Set<String> api = new TreeSet<>();
        api.add("GET");
        api.add("POST");
        api.add("PUT");
        api.add("DELETE");
        api.add("HEAD");
        
        return Response
                .noContent()
                .allow(api)
                .status(Response.Status.NO_CONTENT)
                .build();
    }
    public PersonResource() {
    }
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getPeople(){
        System.out.println("Hello.. Testing");
        List<Person> people = PeopleDao.instance.getPeople();
        GenericEntity<List<Person>> entity = new GenericEntity<List<Person>>(people) {};
        Person person = PeopleDao.instance.getNewestTimestamp();
        String timestamp = person.getTime();
        try{
            Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp);
            CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        return Response
                .status(Response.Status.OK)
                .entity(entity)
                .cacheControl(cc)
                .header("Last-Modified", date1)
//                .header("If-Modified-Since", timestamp)
//                .lastModified(timestamp)
                .build();
        }
        
        
        catch(Exception e){
            e.printStackTrace();
        }
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postPerson(Person person){
        System.out.println(person.toString());
        Person person2 = PeopleDao.instance.getNewestTimestamp();
        String timestamp = person2.getTime();
        try{
            Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp);
             int success = PeopleDao.instance.addPerson(person);
        if (success == 1){
                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .build();
            }
            else if (success == 0){
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }
            else{
                return Response
                        .status(Response.Status.NO_CONTENT)
                        .header("Last-Modified", date1)
                        .build();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
       return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .build();
            
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(Person person){
        System.out.println(person.toString());
        CacheControl cc = new CacheControl();
        cc.setMaxAge(1);
        int success = PeopleDao.instance.updatePerson(person);
        if (success == 1){
                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .build();
            }
            else if (success == 0){
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }
            return Response
                        .status(Response.Status.NO_CONTENT)
                        .cacheControl(cc)
                        .build();
            
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("refresh")
    public Response getPerson(@Context Request request){
        Person person = PeopleDao.instance.getNewestTimestamp();
        String timestamp = person.getTime();
        try{
            Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp);
            ResponseBuilder response = request.evaluatePreconditions(date1);
            System.out.println(request);
            System.out.println(timestamp);
            if(response == null){
                List<Person> people = PeopleDao.instance.getPeople();
                GenericEntity<List<Person>> entity = new GenericEntity<List<Person>>(people) {};
                return Response
                        .status(Response.Status.OK)
                        .entity(entity)
                        .lastModified(date1)
                        .build();
            }
            else{
                return Response
                        .status(Response.Status.NOT_MODIFIED)
                        .lastModified(date1)
                        .build();
            }
         }
        catch(Exception e){
            e.printStackTrace();
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .build();
        }

    
        
    
    
    }
}
