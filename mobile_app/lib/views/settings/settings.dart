import 'package:rvi_analyzer/views/settings/device_settings/device_settings.dart';
import 'package:rvi_analyzer/views/settings/privacy_policy.dart';
import 'package:flutter/material.dart';

class Settings extends StatefulWidget {
  Settings({Key? key}) : super(key: key);

  @override
  State<Settings> createState() => _SettingsState();
}

class _SettingsState extends State<Settings> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color.fromARGB(255, 2, 2, 2),
      appBar: AppBar(
        backgroundColor: Colors.black,
        title: const Text(
          'Setting',
          style: TextStyle(color: Color.fromARGB(255, 255, 255, 255)),
        ),
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.fromLTRB(12.0, 0, 12, 0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const SizedBox(
                height: 20,
              ),
              const Text(
                'Preferences',
                style: TextStyle(
                    fontSize: 16, color: Color.fromARGB(255, 148, 163, 184)),
              ),
              const SizedBox(
                height: 5,
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  const Text(
                    'Device settings',
                    style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.w500,
                        color: Color.fromARGB(255, 255, 255, 255)),
                  ),
                  IconButton(
                      onPressed: () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => DeviceSettings(),
                            ));
                      },
                      icon: const Icon(
                        Icons.chevron_right,
                        color: Colors.grey,
                      ))
                ],
              ),
              const SizedBox(
                height: 20,
              ),
              const Text(
                'Application Settings',
                style: TextStyle(
                    fontSize: 16, color: Color.fromARGB(255, 148, 163, 184)),
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  const Text(
                    'Configurations',
                    style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.w500,
                        color: Color.fromARGB(255, 255, 255, 255)),
                  ),
                  IconButton(
                      onPressed: () {},
                      icon: const Icon(
                        Icons.chevron_right,
                        color: Colors.grey,
                      ))
                ],
              ),
              const SizedBox(
                height: 20,
              ),
              const Text(
                'Support',
                style: TextStyle(
                    fontSize: 16, color: Color.fromARGB(255, 148, 163, 184)),
              ),
              const SizedBox(
                height: 5,
              ),
              // Row(
              //   mainAxisAlignment: MainAxisAlignment.spaceBetween,
              //   children: [
              //     const Text(
              //       'Data',
              //       style: TextStyle(
              //           fontSize: 16,
              //           fontWeight: FontWeight.w500,
              //           color: Color.fromARGB(255, 255, 255, 255)),
              //     ),
              //     IconButton(
              //         onPressed: () {},
              //         icon: const Icon(
              //           Icons.chevron_right,
              //           color: Colors.grey,
              //         ))
              //   ],
              // ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  const Text(
                    'Privacy Policy',
                    style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.w500,
                        color: Color.fromARGB(255, 255, 255, 255)),
                  ),
                  IconButton(
                      onPressed: () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => const PrivacyPolicy(),
                            ));
                      },
                      icon: const Icon(
                        Icons.chevron_right,
                        color: Colors.grey,
                      ))
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
