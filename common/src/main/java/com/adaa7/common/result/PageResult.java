package com.adaa7.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult {
    private long total;
    private long totalSize;
    private long UsedSize;
    private List records;
}
