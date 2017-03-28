package de.codecentric.resilient.connote.rest;

import javax.servlet.http.HttpServletRequest;

import de.codecentric.resilient.connote.service.ConnoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.codecentric.resilient.dto.ConnoteDTO;

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

        return connote;
    }

}
