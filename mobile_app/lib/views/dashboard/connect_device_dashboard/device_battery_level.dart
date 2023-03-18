import 'package:flutter/material.dart';
import 'package:flutter_blue/flutter_blue.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:rvi_analyzer/common/battery_data.dart';
import 'package:rvi_analyzer/providers/device_state_provider.dart';

class BatteryLevelText extends StatefulWidget {
  final ScanResult scanResult;
  const BatteryLevelText(
    this.scanResult, {
    Key? key,
  }) : super(key: key);

  @override
  State<BatteryLevelText> createState() => _BatteryLevelTextState(scanResult);
}

class _BatteryLevelTextState extends State<BatteryLevelText> {
  ScanResult scanResult;
  _BatteryLevelTextState(this.scanResult);

  @override
  Widget build(BuildContext context) {
    return Consumer(builder: ((context, ref, child) {
      return Row(
        children: [
          Text(
            "${ref.watch(ref.watch(deviceDataMap[scanResult.device.name]!).streamData).batteryLevel.toString()}%",
            style: TextStyle(
                fontSize: 13,
                fontWeight: FontWeight.bold,
                color: getBatteryColor(ref
                    .watch(ref
                        .watch(deviceDataMap[scanResult.device.name]!)
                        .streamData)
                    .batteryLevel)),
          ),
          const SizedBox(
            width: 4,
          ),
          Transform.rotate(
            angle: (90 * 22 / 7) / 180,
            child: Icon(
                getBatteryIcon(ref
                    .watch(ref
                        .watch(deviceDataMap[scanResult.device.name]!)
                        .streamData)
                    .batteryLevel),
                color: getBatteryColor(ref
                    .watch(ref
                        .watch(deviceDataMap[scanResult.device.name]!)
                        .streamData)
                    .batteryLevel)),
          ),
        ],
      );
    }));
  }
}
