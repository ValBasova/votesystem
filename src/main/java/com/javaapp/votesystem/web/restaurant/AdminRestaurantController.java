package com.javaapp.votesystem.web.restaurant;

import com.javaapp.votesystem.model.Restaurant;
import com.javaapp.votesystem.service.RestaurantService;
import com.javaapp.votesystem.service.VoteService;
import com.javaapp.votesystem.to.RestaurantToWithMenu;
import com.javaapp.votesystem.to.RestaurantToWithVote;
import com.javaapp.votesystem.util.RestaurantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.javaapp.votesystem.util.ValidationUtil.assureIdConsistent;
import static com.javaapp.votesystem.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminRestaurantController.class);

    static final String REST_URL = "/admin/restaurants";

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteService voteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        LOG.info("create {}", restaurant);
        Restaurant created = restaurantService.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId) {
        LOG.info("delete restaurant {}", restaurantId);
        restaurantService.delete(restaurantId);
    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int restaurantId) {
        assureIdConsistent(restaurant, restaurantId);
        LOG.info("update {}", restaurant);
        restaurantService.update(restaurant);
    }

    @GetMapping("/{restaurantId}")
    public Restaurant get(@PathVariable int restaurantId) {
        LOG.info("get restaurant with id {}", restaurantId);
        return restaurantService.get(restaurantId);
    }

    @GetMapping(value = "/{restaurantId}", params = "date")
    public RestaurantToWithMenu getByDate(@PathVariable int restaurantId,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LOG.info("get restaurant with id {} by date {}", restaurantId, date);
        return RestaurantUtil.createToWithMenu(restaurantService.getByDate(restaurantId, date));
    }

    @GetMapping(value = "/votes", params = "date")
    public List<RestaurantToWithVote> getAllWithVotesByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LOG.info("get all restaurants with votes by date {}", date);
        return RestaurantUtil.getRestaurantsToWithVoteCount(restaurantService.getAllByDate(date),
                voteService.getAllByDate(date));
    }
}