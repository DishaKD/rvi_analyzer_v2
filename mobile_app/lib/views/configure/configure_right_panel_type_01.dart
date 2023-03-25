import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_blue/flutter_blue.dart';
import 'package:rvi_analyzer/views/common/form_eliments/text_input.dart';

class ConfigureRightPanelType01 extends StatefulWidget {
  final ScanResult sc;
  const ConfigureRightPanelType01({Key? key, required this.sc})
      : super(key: key);

  @override
  State<ConfigureRightPanelType01> createState() =>
      _ConfigureRightPanelType01State();
}

class _ConfigureRightPanelType01State extends State<ConfigureRightPanelType01> {
  final voltageController = TextEditingController();
  final maxCurrentController = TextEditingController();
  @override
  Widget build(BuildContext context) {
    double height = MediaQuery.of(context).size.height;
    double width = MediaQuery.of(context).size.width;
    var isLandscape =
        MediaQuery.of(context).orientation == Orientation.landscape;
    return SizedBox(
      width: isLandscape ? (width / 3) * 2 - 32 : width,
      child: SizedBox(
        child: Container(
            decoration: BoxDecoration(
              color: Colors.grey[300],
              borderRadius: BorderRadius.circular(10.0),
              boxShadow: [
                BoxShadow(
                  color: Colors.grey.withOpacity(0.5),
                  spreadRadius: 5,
                  blurRadius: 5,
                  offset: const Offset(0, 0.5), // changes position of shadow
                ),
              ],
            ),
            child: Padding(
              padding: const EdgeInsets.fromLTRB(12, 12, 12, 0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  const Text(
                    "Mode 01",
                    style: TextStyle(
                        fontSize: 30,
                        fontWeight: FontWeight.bold,
                        color: Colors.black54),
                  ),
                  const SizedBox(
                    height: 2.0,
                  ),
                  const SizedBox(
                    height: 10.0,
                  ),
                  getScrollView(),
                  const SizedBox(
                    height: 30.0,
                  ),
                ],
              ),
            )),
      ),
    );
  }

  Widget getScrollView() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Row(
          children: [
            Expanded(
              flex: 1,
              child: TextInput(
                  data: TestInputData(
                      inputType: TextInputType.number,
                      controller: voltageController,
                      validatorFun: (val) {
                        if (val!.isEmpty) {
                          return "Voltage cannot be empty";
                        } else {
                          null;
                        }
                      },
                      labelText: 'Voltage (V)')),
            ),
            const SizedBox(
              width: 5,
            ),
            Expanded(
              flex: 1,
              child: TextInput(
                  data: TestInputData(
                      inputType: TextInputType.number,
                      controller: maxCurrentController,
                      validatorFun: (val) {
                        if (val!.isEmpty) {
                          return "Max current cannot be empty";
                        } else {
                          null;
                        }
                      },
                      labelText: 'Max current (A)')),
            ),
          ],
        ),
        const SizedBox(
          height: 10.0,
        ),
        const Text(
          'Current Range : ',
          style: TextStyle(fontSize: 15, color: Colors.grey),
        ),
        const SizedBox(
          height: 5.0,
        ),
        Row(
          children: [
            Expanded(
              flex: 1,
              child: TextInput(
                  data: TestInputData(
                      controller: voltageController,
                      inputType: TextInputType.number,
                      validatorFun: (val) {
                        if (val!.isEmpty) {
                          return "Max current cannot be empty";
                        } else {
                          null;
                        }
                      },
                      labelText: 'Min (A)')),
            ),
            const SizedBox(
              width: 5,
            ),
            Expanded(
              flex: 1,
              child: TextInput(
                  data: TestInputData(
                      inputType: TextInputType.number,
                      controller: maxCurrentController,
                      validatorFun: (val) {
                        if (val!.isEmpty) {
                          return "Max current cannot be empty";
                        } else {
                          null;
                        }
                      },
                      labelText: 'Max (A)')),
            ),
          ],
        ),
        const SizedBox(
          height: 10.0,
        ),
        const Text(
          'Current Readings : ',
          style: TextStyle(fontSize: 15, color: Colors.grey),
        ),
        const SizedBox(
          height: 5.0,
        ),
        Row(
          children: [
            Expanded(
              flex: 1,
              child: TextInput(
                  data: TestInputData(
                      controller: voltageController,
                      inputType: TextInputType.number,
                      enabled: false,
                      validatorFun: (val) {
                        null;
                      },
                      labelText: 'Voltage (V)')),
            ),
            const SizedBox(
              width: 5,
            ),
            Expanded(
              flex: 1,
              child: TextInput(
                  data: TestInputData(
                      inputType: TextInputType.number,
                      controller: maxCurrentController,
                      enabled: false,
                      validatorFun: (val) {
                        null;
                      },
                      labelText: 'Temp (A)')),
            ),
          ],
        ),
        const SizedBox(
          height: 10,
        ),
        TextInput(
            data: TestInputData(
                controller: maxCurrentController,
                enabled: false,
                validatorFun: (val) {
                  null;
                },
                labelText: 'Resistance (Om)')),
        const SizedBox(
          height: 10,
        ),
        Row(
          children: const [
            Expanded(
              flex: 1,
              child: SizedBox(
                height: 55,
                child: CupertinoButton(
                  disabledColor: Colors.red,
                  color: Colors.cyan,
                  onPressed: null,
                  child: Text(
                    'FAIL',
                    style: TextStyle(
                        color: Color.fromARGB(255, 231, 230, 230),
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
            ),
          ],
        ),
        const SizedBox(
          height: 15,
        ),
        Row(
          children: [
            Expanded(
              flex: 1,
              child: SizedBox(
                height: 55,
                child: CupertinoButton(
                  padding: EdgeInsets.all(0),
                  disabledColor: Colors.grey,
                  color: Colors.cyan,
                  onPressed: () {},
                  child: const Text(
                    'Start',
                    style: TextStyle(
                        color: Color.fromARGB(255, 231, 230, 230),
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
            ),
            const SizedBox(
              width: 5,
            ),
            Expanded(
              flex: 1,
              child: SizedBox(
                height: 55,
                child: CupertinoButton(
                  padding: EdgeInsets.all(0),
                  disabledColor: Colors.grey,
                  color: Colors.green,
                  onPressed: () {},
                  child: const Text(
                    'Save',
                    style: TextStyle(
                        color: Color.fromARGB(255, 231, 230, 230),
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
            ),
          ],
        )
      ],
    );
  }
}
