package com.xiaoyao.hxds.bff.customer.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.xiaoyao.hxds.bff.customer.controller.form.CarAddForm;
import com.xiaoyao.hxds.bff.customer.controller.form.CarDeleteForm;
import com.xiaoyao.hxds.bff.customer.service.CarService;
import com.xiaoyao.hxds.common.result.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@SaCheckLogin
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/car")
public class CarController {
    private final CarService carService;

    @PostMapping("/add")
    public R add(@Valid @RequestBody CarAddForm form) {
        return R.ok(carService.addCar(form));
    }

    @PostMapping("/delete")
    public R deleteCar(@Valid @RequestBody CarDeleteForm form) {
        return R.ok(carService.deleteCar(form));
    }

    @GetMapping("/list")
    public R list() {
        return R.ok(carService.listCar());
    }
}
