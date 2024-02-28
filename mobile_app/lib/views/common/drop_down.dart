import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:rvi_analyzer/providers/device_state_provider.dart';

class DropDownCustom extends ConsumerStatefulWidget {
  final DropDownData dropDownData;
  const DropDownCustom({Key? key, required this.dropDownData})
      : super(key: key);

  @override
  ConsumerState<DropDownCustom> createState() => _DropDownCustomState();
}

class _DropDownCustomState extends ConsumerState<DropDownCustom> {
  @override
  Widget build(BuildContext context) {
    double height = MediaQuery.of(context).size.height;
    double width = MediaQuery.of(context).size.width;
    return Container(
      padding: const EdgeInsets.fromLTRB(20, 2, 20, 0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisAlignment: MainAxisAlignment.start,
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          Row(
            children: [
              Expanded(flex: 6, child: Container()),
              const Expanded(
                flex: 2,
                child: Divider(
                  color: Colors.grey,
                  thickness: 4,
                ),
              ),
              Expanded(flex: 6, child: Container()),
            ],
          ),
          const SizedBox(
            height: 10,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(
                widget.dropDownData.title,
                style: const TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.bold,
                    color: Colors.grey),
              ),
              InkWell(
                onTap: () {
                  Navigator.pop(context);
                },
                child: const Icon(
                  Icons.close,
                  color: Colors.grey,
                ),
              )
            ],
          ),
          const SizedBox(
            height: 20,
          ),
          SizedBox(
            height: (height / 3) - 4,
            child: Scrollbar(
              thumbVisibility: false,
              child: ListView.builder(
                  itemCount: widget.dropDownData.items.length,
                  itemBuilder: (context, index) {
                    return Padding(
                      padding: const EdgeInsets.fromLTRB(0, 0, 0, 10),
                      child: ListTile(
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(15),
                          side: BorderSide(
                              color: ref
                                          .watch(deviceDataMap[
                                              widget.dropDownData.deviceName]!)
                                          .selectedModeId ==
                                      index
                                  ? Colors.cyan
                                  : Colors.grey),
                        ),
                        title: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            SizedBox(
                              width: 50,
                              child: ref
                                          .watch(deviceDataMap[
                                              widget.dropDownData.deviceName]!)
                                          .selectedModeId ==
                                      index
                                  ? const Icon(
                                      Icons.mode_standby_sharp,
                                      color: Colors.cyan,
                                      size: 30,
                                    )
                                  : const Icon(
                                      Icons.mode_standby_sharp,
                                      color: Colors.grey,
                                      size: 30,
                                    ),
                            ),
                            const SizedBox(
                              width: 10,
                            ),
                            Expanded(
                              flex: 1,
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text(widget.dropDownData.items[index].title,
                                      style:
                                          const TextStyle(color: Colors.grey)),
                                  widget.dropDownData.items[index].subTitle !=
                                          null
                                      ? Text(
                                          widget.dropDownData.items[index]
                                              .subTitle!,
                                          style: const TextStyle(
                                              color: Colors.grey))
                                      : Container(),
                                ],
                              ),
                            ),
                            const Spacer(),
                            SizedBox(
                                width: 20,
                                child: ref
                                            .watch(deviceDataMap[widget
                                                .dropDownData.deviceName]!)
                                            .selectedModeId ==
                                        index
                                    ? const Icon(
                                        Icons.check_circle_sharp,
                                        color: Colors.cyan,
                                      )
                                    : const Icon(
                                        Icons.circle_outlined,
                                        color: Colors.grey,
                                      )),
                          ],
                        ),
                        tileColor: Colors.white,
                        onTap: () => {
                          ref
                              .watch(deviceDataMap[
                                  widget.dropDownData.deviceName]!)
                              .updateSelectedModeId(index),
                          widget.dropDownData.updateConnectDevices(
                              widget.dropDownData.items[index]),
                          Navigator.pop(context, 'OK')
                        },
                      ),
                    );
                  }),
            ),
          ),
          const SizedBox(
            height: 20,
          )
        ],
      ),
    );
  }
}

Widget getItem(BuildContext context, DropDownItem item,
    void Function(DropDownItem) updateConnectDevices) {
  return Padding(
    padding: const EdgeInsets.fromLTRB(0, 0, 0, 5),
    child: ListTile(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(15),
        side: const BorderSide(color: Color.fromARGB(255, 34, 197, 94)),
      ),
      title: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Expanded(
            flex: 5,
            child: Column(
              // mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(item.title,
                    style: const TextStyle(
                        color: Color.fromARGB(255, 255, 254, 254))),
                item.subTitle != null
                    ? Text(item.subTitle!,
                        style: const TextStyle(
                            color: Color.fromARGB(255, 255, 255, 255)))
                    : Container(),
              ],
            ),
          ),
          Expanded(flex: 8, child: Container()),
          const Expanded(
            flex: 2,
            child: Icon(
              Icons.check_circle_sharp,
              color: Colors.lightGreen,
            ),
          ),
        ],
      ),
      tileColor: const Color.fromARGB(255, 30, 41, 59),
      onTap: () => {
        // setState(() {
        //   selectedIndex = index;
        // }),
        updateConnectDevices(item)
      },
    ),
  );
}

class DropDownData {
  final String title;
  final List<DropDownItem> items;
  final void Function(DropDownItem) updateConnectDevices;
  final String deviceName;

  DropDownData(
      this.title, this.items, this.updateConnectDevices, this.deviceName);
}

class DropDownItem {
  final String title;
  final String? subTitle;
  final int index;

  DropDownItem(this.title, this.subTitle, this.index);
}
