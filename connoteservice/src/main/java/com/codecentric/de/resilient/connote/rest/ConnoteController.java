package com.codecentric.de.resilient.connote.rest;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codecentric.de.resilient.dto.ConnoteDTO;
import com.codecentric.de.resilient.connote.service.ConnoteService;

/**
 * @author Benjamin Wilms
 */
@RestController
@RequestMapping("rest/connote")
public class ConnoteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnoteController.class);

    private ConnoteService connoteService;

    @Autowired
    public ConnoteController(ConnoteService connoteService) {
        this.connoteService = connoteService;
    }

    @RequestMapping("create")
    public ConnoteDTO createDefault(HttpServletRequest request) {
        LOGGER.debug(LOGGER.isDebugEnabled() ? "Create new connote" : null);

        ConnoteDTO connote = connoteService.createConnote();
        connote.setInstance(request.getLocalName() + " : " + request.getLocalPort());

        return connote;
    }

}
